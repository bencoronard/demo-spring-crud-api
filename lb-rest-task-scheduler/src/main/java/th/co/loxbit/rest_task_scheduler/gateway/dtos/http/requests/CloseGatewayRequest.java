package th.co.loxbit.rest_task_scheduler.gateway.dtos.http.requests;

import lombok.Data;

@Data
public class CloseGatewayRequest {

  private String maintenanceMessage;

}
