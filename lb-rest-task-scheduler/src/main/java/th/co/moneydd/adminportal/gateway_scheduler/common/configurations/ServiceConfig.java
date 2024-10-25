package th.co.moneydd.adminportal.gateway_scheduler.common.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import th.co.moneydd.adminportal.gateway_scheduler.common.factories.ConfigurableObjectFactory;
import th.co.moneydd.adminportal.gateway_scheduler.common.http.configurers.RequestInterceptorConfig;
import th.co.moneydd.adminportal.gateway_scheduler.common.http.configurers.RestServiceConfig;
import th.co.moneydd.adminportal.gateway_scheduler.common.http.interceptors.RequestInterceptor;
import th.co.moneydd.adminportal.gateway_scheduler.common.http.services.RestService;
import th.co.moneydd.adminportal.gateway_scheduler.common.http.services.implementations.RestServiceImpl;

@Configuration
@RequiredArgsConstructor
public class ServiceConfig {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ConfigurableObjectFactory<RequestInterceptor, RequestInterceptorConfig> interceptorFactory;
  private final ConfigurableObjectFactory<RestServiceImpl, RestServiceConfig> restServiceFactory;

  // ---------------------------------------------------------------------------//
  // Configurations
  // ---------------------------------------------------------------------------//

  @Bean("gatewayRestService")
  RestService gatewayRestService() {

    RequestInterceptor interceptor = interceptorFactory.create(
        RequestInterceptorConfig.builder()
            .apiKey(null)
            .build());

    return restServiceFactory.create(
        RestServiceConfig.builder()
            .baseUrl(null)
            .interceptor(interceptor)
            .build());
  }

}
