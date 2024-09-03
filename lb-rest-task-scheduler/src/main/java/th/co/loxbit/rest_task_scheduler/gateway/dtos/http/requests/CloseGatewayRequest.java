package th.co.loxbit.rest_task_scheduler.gateway.dtos.http.requests;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CloseGatewayRequest {

  private String maintenanceMessage;

}
