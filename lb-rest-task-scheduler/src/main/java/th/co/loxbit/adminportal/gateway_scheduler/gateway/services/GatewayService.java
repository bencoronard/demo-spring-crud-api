package th.co.loxbit.adminportal.gateway_scheduler.gateway.services;

import java.time.Instant;

import th.co.loxbit.adminportal.gateway_scheduler.gateway.entities.GatewayStatus;

public interface GatewayService {

  int SERVICE_CODE = 5000;

  GatewayStatus getStatus();

  void open();

  void close(String message, Instant start, Instant end);

  void openOverride(String performedBy);

  void closeOverride(Instant end, String performedBy);

}
