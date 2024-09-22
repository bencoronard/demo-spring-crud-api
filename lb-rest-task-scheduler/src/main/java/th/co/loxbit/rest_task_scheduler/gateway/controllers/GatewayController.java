package th.co.loxbit.rest_task_scheduler.gateway.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import th.co.loxbit.rest_task_scheduler.common.http.dtos.responses.GlobalResponseBody;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.requests.inbound.CloseGatewayRequestInbound;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.outbound.GetGatewayStatusResponseOutbound;

@RequestMapping("/api/gateway")
public interface GatewayController {

  String USER_ID_KEY = "USER_ID";

  @GetMapping("/status")
  ResponseEntity<GlobalResponseBody<GetGatewayStatusResponseOutbound>> getGatewayStatus();

  @PostMapping("/open")
  ResponseEntity<GlobalResponseBody<Void>> openGatewayOverride(@RequestAttribute(USER_ID_KEY) String userId);

  @PostMapping("/close")
  ResponseEntity<GlobalResponseBody<Void>> closeGatewayOverride(@RequestBody CloseGatewayRequestInbound request,
      @RequestAttribute(USER_ID_KEY) String userId);

}
