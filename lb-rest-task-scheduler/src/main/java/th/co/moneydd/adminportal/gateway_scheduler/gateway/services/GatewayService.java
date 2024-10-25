package th.co.moneydd.adminportal.gateway_scheduler.gateway.services;

import java.time.Instant;

import th.co.moneydd.adminportal.gateway_scheduler.gateway.entities.GatewayStatus;

public interface GatewayService {

  int SERVICE_CODE = 5000;

  GatewayStatus getGatewayStatus();

  void openGateway();

  void closeGateway(String message, Instant start, Instant end);

  void openGatewayOverride(String performedBy);

  void closeGatewayOverride(Instant end, String performedBy);

}
