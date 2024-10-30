package th.co.loxbit.adminportal.gateway_scheduler.gateway.controllers.implementations;

import java.time.Instant;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.adminportal.gateway_scheduler.audit.entities.AuditRecordType;
import th.co.loxbit.adminportal.gateway_scheduler.audit.service.AuditRecordService;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.dtos.responses.GlobalResponseBody;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.utilities.ResponseBodyUtils;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.controllers.GatewayController;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.requests.inbound.CloseGatewayRequestInbound;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.responses.outbound.GetGatewayStatusResponseOutbound;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.entities.GatewayStatus;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.services.GatewayService;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.entities.Job;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.services.JobService;

@RestController
@RequiredArgsConstructor
public class GatewayControllerImpl implements GatewayController {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final GatewayService gatewayService;
  private final JobService jobService;
  private final AuditRecordService auditRecordService;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<GetGatewayStatusResponseOutbound>> getGatewayStatus() {

    GatewayStatus status = gatewayService.getStatus();

    GlobalResponseBody<GetGatewayStatusResponseOutbound> responseBody = ResponseBodyUtils.createSuccessResponseBody(
        "Gateway Status",
        GetGatewayStatusResponseOutbound.builder()
            .gatewayIsOpen(status == GatewayStatus.OPEN ? true : false)
            .build());

    HttpHeaders headers = new HttpHeaders();
    headers.setCacheControl("no-store, no-cache, must-revalidate, max-age=0");
    headers.setPragma("no-cache");
    headers.setExpires(0);

    return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> openGatewayOverride(String userId) {

    gatewayService.openOverride(userId);

    Instant now = Instant.now();

    auditRecordService.record(GatewayStatus.OPEN.getStatus(), now, now, userId, AuditRecordType.OVERRIDE);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody("Gateway opened", null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> closeGatewayOverride(CloseGatewayRequestInbound request,
      String userId) {

    Instant reopenAt = Instant.ofEpochSecond(request.end());

    Job job = jobService.scheduleJobPartial(reopenAt, userId);

    gatewayService.closeOverride(reopenAt, userId);

    auditRecordService.record(job.getId(), Instant.now(), reopenAt, userId, AuditRecordType.OVERRIDE);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody("Gateway closed", null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

}
