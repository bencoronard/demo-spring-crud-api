package th.co.loxbit.rest_task_scheduler.gateway.services;

import th.co.loxbit.rest_task_scheduler.gateway.entities.GatewayStatus;

public interface GatewayService {

  int SERVICE_CODE = 5000;

  GatewayStatus getGatewayStatus();

  void openGateway(String createdBy);

  void closeGateway(String message, String createdBy);

}
