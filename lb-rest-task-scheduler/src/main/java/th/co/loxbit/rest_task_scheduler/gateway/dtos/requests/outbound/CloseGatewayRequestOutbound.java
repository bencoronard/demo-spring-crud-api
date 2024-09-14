package th.co.loxbit.rest_task_scheduler.gateway.dtos.requests.outbound;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CloseGatewayRequestOutbound {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String message;

}
