package th.co.loxbit.adminportal.gateway_scheduler.gateway.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import th.co.loxbit.adminportal.gateway_scheduler.common.http.dtos.responses.GlobalResponseBody;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.requests.inbound.CloseGatewayRequestInbound;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.responses.outbound.GetGatewayStatusResponseOutbound;

@RequestMapping("/v1/goodmoney-admin-portal/gateway")
public interface GatewayController {

  String USER_ID_KEY = "USER_ID";

  @GetMapping("/status")
  ResponseEntity<GlobalResponseBody<GetGatewayStatusResponseOutbound>> getGatewayStatus();

  @PutMapping("/open")
  ResponseEntity<GlobalResponseBody<Void>> openGatewayOverride(@RequestAttribute(USER_ID_KEY) String userId);

  @PutMapping("/close")
  ResponseEntity<GlobalResponseBody<Void>> closeGatewayOverride(@RequestBody CloseGatewayRequestInbound request,
      @RequestAttribute(USER_ID_KEY) String userId);

}
