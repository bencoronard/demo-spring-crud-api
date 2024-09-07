package th.co.loxbit.rest_task_scheduler.common.http.factories;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.common.factories.ConfigurableObjectFactory;
import th.co.loxbit.rest_task_scheduler.common.http.configurers.RestServiceConfig;
import th.co.loxbit.rest_task_scheduler.common.http.services.implementations.RestServiceImpl;

@Component
@RequiredArgsConstructor
public class RestServiceFactory implements ConfigurableObjectFactory<RestServiceImpl, RestServiceConfig> {

  private final RestClient.Builder restClientBuilder;

  @Override
  public RestServiceImpl create(RestServiceConfig config) {

    RestClient.Builder builder = restClientBuilder.clone();

    if (config.getBaseUrl() != null) {
      builder.baseUrl(config.getBaseUrl());
    }

    if (config.getInterceptor() != null) {
      builder.requestInterceptor(config.getInterceptor());
    }

    return new RestServiceImpl(builder.build());
  }

}
