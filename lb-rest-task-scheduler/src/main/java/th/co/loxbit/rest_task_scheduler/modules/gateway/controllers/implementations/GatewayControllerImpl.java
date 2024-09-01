package th.co.loxbit.rest_task_scheduler.modules.gateway.controllers.implementations;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.modules.common.entities.http.GlobalResponseBody;
import th.co.loxbit.rest_task_scheduler.modules.gateway.controllers.GatewayController;
import th.co.loxbit.rest_task_scheduler.modules.gateway.services.GatewayService;

@RestController
@RequiredArgsConstructor
public class GatewayControllerImpl implements GatewayController {

  private final GatewayService gatewayService;

  @Override
  public ResponseEntity<GlobalResponseBody<String>> getGatewayStatus() {
    
    // TODO Auto-generated method stub
    gatewayService.getGatewayStatus();
    throw new UnsupportedOperationException("Unimplemented method 'getGatewayStatus'");
  }

  @Override
  public ResponseEntity<GlobalResponseBody<String>> openGateway() {
    // TODO Auto-generated method stub
    gatewayService.openGateway();
    throw new UnsupportedOperationException("Unimplemented method 'openGateway'");
  }

  @Override
  public ResponseEntity<GlobalResponseBody<String>> closeGateway() {
    // TODO Auto-generated method stub
    gatewayService.closeGateway();
    throw new UnsupportedOperationException("Unimplemented method 'closeGateway'");
  }

}
