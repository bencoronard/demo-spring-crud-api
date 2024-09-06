package th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.outbound;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetGatewayStatusResponseOutbound {

  private final boolean isGatewayOpen;

}
