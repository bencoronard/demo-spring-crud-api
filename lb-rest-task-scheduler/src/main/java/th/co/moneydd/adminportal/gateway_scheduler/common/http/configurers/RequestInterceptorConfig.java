package th.co.moneydd.adminportal.gateway_scheduler.common.http.configurers;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestInterceptorConfig {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String apiKey;

}
