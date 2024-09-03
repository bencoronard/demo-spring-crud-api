package th.co.loxbit.rest_task_scheduler.gateway.services;

import th.co.loxbit.rest_task_scheduler.gateway.utilities.GatewayStatus;

public interface GatewayService {

  GatewayStatus getGatewayStatus();

  void openGateway();

  void closeGateway();

}
