package th.co.loxbit.rest_task_scheduler.common.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.common.factories.ConfigurableObjectFactory;
import th.co.loxbit.rest_task_scheduler.common.http.configurers.RequestInterceptorConfig;
import th.co.loxbit.rest_task_scheduler.common.http.configurers.RestServiceConfig;
import th.co.loxbit.rest_task_scheduler.common.http.entities.RequestInterceptor;
import th.co.loxbit.rest_task_scheduler.common.http.services.RestService;
import th.co.loxbit.rest_task_scheduler.common.http.services.implementations.RestServiceImpl;

@Configuration
@RequiredArgsConstructor
public class ServiceConfig {

  private final ConfigurableObjectFactory<RequestInterceptor, RequestInterceptorConfig> interceptorFactory;
  private final ConfigurableObjectFactory<RestServiceImpl, RestServiceConfig> restServiceFactory;

  @Bean("gatewayRestService")
  RestService gatewayRestService(
      @Value("${api.external.gateway.uri}") String baseUrl,
      @Value("${api.external.gateway.secret.key}") String apiKey) {

    RequestInterceptor interceptor = interceptorFactory.create(
        RequestInterceptorConfig.builder()
            .apiKey(apiKey)
            .build());

    return restServiceFactory.create(
        RestServiceConfig.builder()
            .baseUrl(baseUrl)
            .interceptor(interceptor)
            .build());
  }

}
