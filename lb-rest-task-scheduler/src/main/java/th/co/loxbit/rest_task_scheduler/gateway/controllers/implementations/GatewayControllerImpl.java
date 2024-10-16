package th.co.loxbit.rest_task_scheduler.gateway.controllers.implementations;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.common.http.dtos.responses.GlobalResponseBody;
import th.co.loxbit.rest_task_scheduler.common.http.utilities.ResponseBodyUtils;
import th.co.loxbit.rest_task_scheduler.gateway.controllers.GatewayController;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.requests.inbound.CloseGatewayRequestInbound;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.outbound.GetGatewayStatusResponseOutbound;
import th.co.loxbit.rest_task_scheduler.gateway.entities.GatewayStatus;
import th.co.loxbit.rest_task_scheduler.gateway.services.GatewayService;

@RestController
@RequiredArgsConstructor
public class GatewayControllerImpl implements GatewayController {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final GatewayService gatewayService;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<GetGatewayStatusResponseOutbound>> getGatewayStatus() {

    GatewayStatus data = gatewayService.getGatewayStatus();

    GlobalResponseBody<GetGatewayStatusResponseOutbound> responseBody = ResponseBodyUtils.createSuccessResponseBody(
        "gateway status",
        GetGatewayStatusResponseOutbound.builder()
            .isGatewayOpen(data == GatewayStatus.OPEN ? true : false)
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

    gatewayService.openGatewayOverride(userId);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody(null, null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> closeGatewayOverride(CloseGatewayRequestInbound request,
      String userId) {

    gatewayService.closeGatewayOverride(request.message(), userId);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody(null, null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

}
