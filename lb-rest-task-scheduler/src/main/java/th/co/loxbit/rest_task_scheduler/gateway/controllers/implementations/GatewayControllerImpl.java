package th.co.loxbit.rest_task_scheduler.gateway.controllers.implementations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.gateway.controllers.GatewayController;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.http.requests.CloseGatewayRequestInbound;
import th.co.loxbit.rest_task_scheduler.gateway.services.GatewayService;
import th.co.loxbit.rest_task_scheduler.gateway.utilities.GatewayStatus;
import th.co.loxbit.rest_task_scheduler.http.responses.GlobalResponseBody;
import th.co.loxbit.rest_task_scheduler.http.utilities.ResponseUtils;

@RestController
@RequiredArgsConstructor
public class GatewayControllerImpl implements GatewayController {

  private final GatewayService gatewayService;

  @Override
  public ResponseEntity<GlobalResponseBody<String>> getGatewayStatus() {
    GatewayStatus data = gatewayService.getGatewayStatus();
    GlobalResponseBody<String> responseBody =
      ResponseUtils.createSuccessResponseBody(
        "Gateway status",
        data.toString()
      );
    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<GlobalResponseBody<String>> openGateway() {
    gatewayService.openGateway();
    GlobalResponseBody<String> responseBody =
      ResponseUtils.createSuccessResponseBody(
        "Result",
        "OK"
      );
    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<GlobalResponseBody<String>> closeGateway(@Valid CloseGatewayRequestInbound request) {
    gatewayService.closeGateway(request.getMaintenanceMessage());
    GlobalResponseBody<String> responseBody =
      ResponseUtils.createSuccessResponseBody(
        "Result",
        "OK"
      );                   
    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

}
