package th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.requests.outbound;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.KongConfig;

@Builder
@Getter
public class KongGatewayRequestOutbound {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  @JsonProperty("config")
  private final KongConfig config;
  @JsonProperty("enabled")
  private final Boolean enabled;

}
