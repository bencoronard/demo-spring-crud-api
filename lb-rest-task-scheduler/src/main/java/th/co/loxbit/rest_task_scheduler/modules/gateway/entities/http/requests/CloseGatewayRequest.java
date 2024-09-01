package th.co.loxbit.rest_task_scheduler.modules.gateway.entities.http.requests;

import lombok.Data;

@Data
public class CloseGatewayRequest {

  private String maintenanceMessage;

}
