package th.co.loxbit.rest_task_scheduler.common.http.factories;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.common.factories.ConfigurableObjectFactory;
import th.co.loxbit.rest_task_scheduler.common.http.configurers.RestServiceConfigurer;
import th.co.loxbit.rest_task_scheduler.common.http.services.implementations.RestServiceImpl;

@Component
@RequiredArgsConstructor
public class RestServiceFactory implements ConfigurableObjectFactory<RestServiceImpl, RestServiceConfigurer> {

  private final RestClient.Builder restClientBuilder;

  @Override
  public RestServiceImpl create(RestServiceConfigurer config) {
    return new RestServiceImpl(restClientBuilder, config);
  }

}
