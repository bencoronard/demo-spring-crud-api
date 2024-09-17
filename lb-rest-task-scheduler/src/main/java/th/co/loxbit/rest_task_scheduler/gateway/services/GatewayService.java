package th.co.loxbit.rest_task_scheduler.gateway.services;

import th.co.loxbit.rest_task_scheduler.gateway.entities.GatewayStatus;

public interface GatewayService {

  int SERVICE_CODE = 5000;

  GatewayStatus getGatewayStatus();

  void openGateway();

  void closeGateway(String message);

  void openGatewayOverride(String performedBy);

  void closeGatewayOverride(String message, String performedBy);

}
