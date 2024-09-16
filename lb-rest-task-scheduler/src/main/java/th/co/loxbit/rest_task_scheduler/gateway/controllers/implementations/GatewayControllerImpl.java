package th.co.loxbit.rest_task_scheduler.gateway.controllers.implementations;

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

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> openGatewayOverride() {

    gatewayService.openGateway(null);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody(null, null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> closeGatewayOverride(CloseGatewayRequestInbound request) {

    gatewayService.closeGateway(request.message(), null);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody(null, null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

}
