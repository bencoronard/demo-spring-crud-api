package th.co.loxbit.rest_task_scheduler.gateway.controllers.implementations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.common.http.responses.GlobalResponseBody;
import th.co.loxbit.rest_task_scheduler.common.http.utilities.ResponseBodyUtils;
import th.co.loxbit.rest_task_scheduler.gateway.controllers.GatewayController;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.outbound.GetGatewayStatusResponseOutbound;
import th.co.loxbit.rest_task_scheduler.gateway.entities.GatewayStatus;
import th.co.loxbit.rest_task_scheduler.gateway.services.GatewayService;

@RestController
@RequiredArgsConstructor
public class GatewayControllerImpl implements GatewayController {

  private final GatewayService gatewayService;

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

}
