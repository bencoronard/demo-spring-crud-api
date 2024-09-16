package th.co.loxbit.rest_task_scheduler.gateway.services.implementations;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.audit.entities.AuditRecordType;
import th.co.loxbit.rest_task_scheduler.audit.service.AuditRecordService;
import th.co.loxbit.rest_task_scheduler.common.factories.RetryTemplateFactory;
import th.co.loxbit.rest_task_scheduler.common.http.services.RestService;
import th.co.loxbit.rest_task_scheduler.common.utilities.ServiceExceptionUtil;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.requests.outbound.CloseGatewayRequestOutbound;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.inbound.CloseGatewayResponseInbound;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.inbound.GetGatewayStatusResponseInbound;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.inbound.OpenGatewayResponseInbound;
import th.co.loxbit.rest_task_scheduler.gateway.entities.GatewayStatus;
import th.co.loxbit.rest_task_scheduler.gateway.services.GatewayService;

@Service
@RequiredArgsConstructor
public class GatewayServiceImpl implements GatewayService {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  @Qualifier("gatewayRestService")
  private final RestService restService;
  private final AuditRecordService auditRecordService;
  private final RetryTemplateFactory retry;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public GatewayStatus getGatewayStatus() {
    return ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      GetGatewayStatusResponseInbound response = restService.get(
          "/status",
          GetGatewayStatusResponseInbound.class);

      return GatewayStatus.fromStatus(response.desc());

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void openGateway(String createdBy) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      restService.postWithRetry(
          "/open",
          null,
          OpenGatewayResponseInbound.class,
          retry.withExponentialBackOff(3, 1000, 2, 3000));

      long now = Instant.now().getEpochSecond();

      auditRecordService.addAuditRecord(GatewayStatus.OPEN.getStatus(), now, now, createdBy, AuditRecordType.OVERRIDE);

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void closeGateway(String message, String createdBy) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      CloseGatewayRequestOutbound requestBody = CloseGatewayRequestOutbound.builder()
          .message(message)
          .build();

      restService.postWithRetry(
          "/close",
          requestBody,
          CloseGatewayResponseInbound.class,
          retry.withFixedBackOff(3, 1000));

      long now = Instant.now().getEpochSecond();

      auditRecordService.addAuditRecord(GatewayStatus.CLOSED.getStatus(), now, now, createdBy,
          AuditRecordType.OVERRIDE);

      return null;

    }, SERVICE_CODE);
  }

}
