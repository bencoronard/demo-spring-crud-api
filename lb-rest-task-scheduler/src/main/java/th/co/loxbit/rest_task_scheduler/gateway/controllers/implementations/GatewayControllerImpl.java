package th.co.loxbit.rest_task_scheduler.gateway.controllers.implementations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.gateway.controllers.GatewayController;
import th.co.loxbit.rest_task_scheduler.gateway.services.GatewayService;
import th.co.loxbit.rest_task_scheduler.gateway.utilities.GatewayStatus;
import th.co.loxbit.rest_task_scheduler.http.responses.GlobalResponseBody;

@RestController
@RequiredArgsConstructor
public class GatewayControllerImpl implements GatewayController {

  private final GatewayService gatewayService;

  @Override
  public ResponseEntity<GlobalResponseBody<String>> getGatewayStatus() {

    GatewayStatus data = gatewayService.getGatewayStatus();

    GlobalResponseBody<String> responseBody =
      GlobalResponseBody.<String>builder()
                        .exitCode("0000")
                        .desc("status")
                        .payload(data.getStatus())
                        .build();

    return new ResponseEntity<>(responseBody,HttpStatus.OK);
  }

  @Override
  public ResponseEntity<GlobalResponseBody<String>> openGateway() {

    gatewayService.openGateway();

    GlobalResponseBody<String> responseBody =
      GlobalResponseBody.<String>builder()
                        .exitCode("0000")
                        .desc("result")
                        .payload("success")
                        .build();

    return new ResponseEntity<>(responseBody,HttpStatus.OK);
  }

  @Override
  public ResponseEntity<GlobalResponseBody<String>> closeGateway() {

    gatewayService.closeGateway("Hello, world");

    GlobalResponseBody<String> responseBody = 
      GlobalResponseBody.<String>builder()
                        .exitCode("0000")
                        .desc("result")
                        .payload("success")
                        .build();
                        
    return new ResponseEntity<>(responseBody,HttpStatus.OK);
  }

}
