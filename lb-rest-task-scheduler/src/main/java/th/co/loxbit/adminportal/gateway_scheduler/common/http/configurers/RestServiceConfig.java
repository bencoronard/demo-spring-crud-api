package th.co.loxbit.adminportal.gateway_scheduler.common.http.configurers;

import org.springframework.http.client.ClientHttpRequestInterceptor;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RestServiceConfig {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String baseUrl;
  private final ClientHttpRequestInterceptor interceptor;

}
