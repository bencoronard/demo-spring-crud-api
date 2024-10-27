package th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.responses.outbound;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetGatewayStatusResponseOutbound {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final boolean gatewayIsOpen;

}
