package th.co.loxbit.rest_task_scheduler.gateway.dtos.requests;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CloseGatewayRequestOutbound {

  private final String maintenanceMessage;

}
