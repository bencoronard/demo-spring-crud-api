package th.co.loxbit.adminportal.gateway_scheduler.common.security.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import th.co.loxbit.adminportal.gateway_scheduler.common.security.filters.ApiKeyFilter;
import th.co.loxbit.adminportal.gateway_scheduler.common.security.filters.UserIdFilter;

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
  FilterRegistrationBean<ApiKeyFilter> apiKeyFilter() {
    FilterRegistrationBean<ApiKeyFilter> filter = new FilterRegistrationBean<>();
    filter.setFilter(new ApiKeyFilter(apiKey));
    filter.setOrder(0);
    filter.addUrlPatterns("/v1/*");
    return filter;
  }

  // ---------------------------------------------------------------------------//

  @Bean
  FilterRegistrationBean<UserIdFilter> userIdFilter() {
    FilterRegistrationBean<UserIdFilter> filter = new FilterRegistrationBean<>();
    filter.setFilter(new UserIdFilter());
    filter.setOrder(1);
    filter.addUrlPatterns("/v1/*");
    return filter;
  }

}
