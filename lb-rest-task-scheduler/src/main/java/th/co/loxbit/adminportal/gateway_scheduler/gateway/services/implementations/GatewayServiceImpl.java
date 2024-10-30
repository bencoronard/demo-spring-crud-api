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
import th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.WrappableException;
import th.co.loxbit.adminportal.gateway_scheduler.common.factories.RetryTemplateFactory;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.services.RestService;
import th.co.loxbit.adminportal.gateway_scheduler.common.utilities.ServiceExceptionUtil;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.requests.outbound.KongGatewayRequestOutbound;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.responses.inbound.KongGatewayResponseInbound;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.entities.GatewayStatus;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.services.GatewayService;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.utilities.KongUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class GatewayServiceImpl implements GatewayService {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  @Qualifier("gatewayRestService")
  private final RestService restService;

  private final KongUtil kongUtil;
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
  public GatewayStatus getStatus() {
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
  public void open() {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {
      sendRequestsConcurrently(kongUtil.createOpenGatewayRequestBody());
      log.warn("Opened gateway via scheduled job");
      return null;
    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void close(String message, Instant start, Instant end) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {
      sendRequestsConcurrently(kongUtil.createCloseGatewayRequestBody(message, start, end));
      log.warn("Closed gateway via scheduled job");
      return null;
    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void openOverride(String performedBy) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {
      sendRequestsConcurrently(kongUtil.createOpenGatewayRequestBody());
      return null;
    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void closeOverride(Instant end, String performedBy) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {
      sendRequestsConcurrently(kongUtil.createCloseGatewayOverrideRequestBody(end));
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
