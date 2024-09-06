package th.co.loxbit.rest_task_scheduler.gateway.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.loxbit.rest_task_scheduler.common.http.responses.GlobalResponseBody;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.outbound.GetGatewayStatusResponseOutbound;

@RestController
@RequestMapping("/gateway")
public interface GatewayController {

  @GetMapping("/status")
  ResponseEntity<GlobalResponseBody<GetGatewayStatusResponseOutbound>> getGatewayStatus();

}
