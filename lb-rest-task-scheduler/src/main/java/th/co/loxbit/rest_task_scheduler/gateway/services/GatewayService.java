package th.co.loxbit.rest_task_scheduler.gateway.services;

import th.co.loxbit.rest_task_scheduler.gateway.utilities.GatewayStatus;

public interface GatewayService {

  int SERVICE_CODE = 1000;

  GatewayStatus getGatewayStatus();

  void openGateway();

  void closeGateway(String message);

}
