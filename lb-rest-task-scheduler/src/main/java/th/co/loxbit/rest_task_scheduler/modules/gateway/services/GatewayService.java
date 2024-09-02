package th.co.loxbit.rest_task_scheduler.modules.gateway.services;

import th.co.loxbit.rest_task_scheduler.modules.gateway.entities.GatewayStatus;

public interface GatewayService {

  GatewayStatus getGatewayStatus();

  void openGateway();

  void closeGateway();

}
