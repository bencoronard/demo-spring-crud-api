package th.co.loxbit.rest_task_scheduler.modules.gateway.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.loxbit.rest_task_scheduler.modules.common.entities.http.GlobalResponseBody;

@RestController
@RequestMapping("/gateway")
public interface GatewayController {

  @GetMapping("/status")
  ResponseEntity<GlobalResponseBody<String>> getGatewayStatus();

  @PostMapping("/open")
  ResponseEntity<GlobalResponseBody<String>> openGateway();

  @PostMapping("/close")
  ResponseEntity<GlobalResponseBody<String>> closeGateway();

}
