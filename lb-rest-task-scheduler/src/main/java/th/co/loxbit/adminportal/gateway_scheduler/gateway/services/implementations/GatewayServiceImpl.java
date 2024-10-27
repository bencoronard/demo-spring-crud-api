package th.co.loxbit.adminportal.gateway_scheduler.gateway.services.implementations;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import th.co.loxbit.adminportal.gateway_scheduler.audit.entities.AuditRecordType;
import th.co.loxbit.adminportal.gateway_scheduler.audit.service.AuditRecordService;
import th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.WrappableException;
import th.co.loxbit.adminportal.gateway_scheduler.common.factories.RetryTemplateFactory;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.services.RestService;
import th.co.loxbit.adminportal.gateway_scheduler.common.utilities.KongRequestUtil;
import th.co.loxbit.adminportal.gateway_scheduler.common.utilities.ServiceExceptionUtil;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.requests.outbound.KongGatewayRequestOutbound;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.responses.inbound.KongGatewayResponseInbound;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.entities.GatewayStatus;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.services.GatewayService;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayServiceImpl implements GatewayService {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  @Qualifier("gatewayRestService")
  private final RestService restService;
  private final AuditRecordService auditRecordService;

  private final KongRequestUtil kongRequestUtil;
  private final ObjectMapper objectMapper;
  private final RetryTemplateFactory retry;

  @Value("${api.external.gateway.uri}")
  private List<String> gatewayUrlList;
  @Value("${api.external.gateway.status.uri}")
  private String gatewayStatusUrl;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public GatewayStatus getGatewayStatus() {
    return ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      try {
        restService.get(gatewayStatusUrl, String.class);
      } catch (WrappableException e) {
        return GatewayStatus.CLOSED;
      }

      return GatewayStatus.OPEN;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void openGateway() {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      KongGatewayRequestOutbound requestBody = kongRequestUtil.createOpenGatewayRequestBody();

      sendRequestsConcurrently(requestBody);

      log.warn("Opened gateway");

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void closeGateway(String message, Instant start, Instant end) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      KongGatewayRequestOutbound requestBody = kongRequestUtil.createCloseGatewayRequestBody(message, start, end);

      sendRequestsConcurrently(requestBody);

      log.warn("Closed gateway");

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void openGatewayOverride(String performedBy) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      KongGatewayRequestOutbound requestBody = kongRequestUtil.createOpenGatewayRequestBody();

      sendRequestsConcurrently(requestBody);

      Instant now = Instant.now();

      auditRecordService.addRecord(GatewayStatus.OPEN.getStatus(), now, now, performedBy,
          AuditRecordType.OVERRIDE);

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void closeGatewayOverride(Instant end, String performedBy) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      KongGatewayRequestOutbound requestBody = kongRequestUtil.createCloseGatewayOverrideRequestBody(end);

      sendRequestsConcurrently(requestBody);

      Instant now = Instant.now();

      auditRecordService.addRecord(GatewayStatus.CLOSED.getStatus(), now, now, performedBy, AuditRecordType.OVERRIDE);

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//
  // Utilities
  // ---------------------------------------------------------------------------//

  private void sendRequestsConcurrently(KongGatewayRequestOutbound body)
      throws InterruptedException, ExecutionException, JsonProcessingException {

    try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {

      log.info("Body >>>" + objectMapper.writeValueAsString(body));

      List<Future<KongGatewayResponseInbound>> futures = new ArrayList<>();

      for (String url : gatewayUrlList) {
        futures.add(executor.submit(
            () -> restService.patch(url, MediaType.APPLICATION_JSON, body, KongGatewayResponseInbound.class,
                retry.withFixedBackOff(3, 5000))));
      }

      for (Future<KongGatewayResponseInbound> future : futures) {
        KongGatewayResponseInbound response = future.get();
        log.info(response.enabled() ? "Kong closed" : "Kong opened");
      }

    }

  }

}
