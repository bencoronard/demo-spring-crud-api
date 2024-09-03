package th.co.loxbit.rest_task_scheduler.gateway.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.loxbit.rest_task_scheduler.http.responses.GlobalResponseBody;

@RestController
@RequestMapping("/gateway")
public interface GatewayController {

  int SERVICE_CODE = 1000;

  @GetMapping("/status")
  ResponseEntity<GlobalResponseBody<String>> getGatewayStatus();

  @PostMapping("/open")
  ResponseEntity<GlobalResponseBody<String>> openGateway();

  @PostMapping("/close")
  ResponseEntity<GlobalResponseBody<String>> closeGateway();

}
