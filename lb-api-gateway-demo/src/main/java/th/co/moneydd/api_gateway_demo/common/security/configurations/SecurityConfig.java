package th.co.moneydd.api_gateway_demo.common.security.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import th.co.moneydd.api_gateway_demo.common.security.filters.ApiKeyFilter;

@Configuration
public class SecurityConfig {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  @Value("${api.internal.secret.key}")
  private String apiKey;

  // ---------------------------------------------------------------------------//
  // Configurations
  // ---------------------------------------------------------------------------//

  @Bean
  ApiKeyFilter apiKeyFilter() {
    return new ApiKeyFilter(apiKey);
  }

}
