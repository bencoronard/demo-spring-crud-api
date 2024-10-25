package th.co.moneydd.adminportal.gateway_scheduler.common.http.factories;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import th.co.moneydd.adminportal.gateway_scheduler.common.factories.ConfigurableObjectFactory;
import th.co.moneydd.adminportal.gateway_scheduler.common.http.configurers.RestServiceConfig;
import th.co.moneydd.adminportal.gateway_scheduler.common.http.services.implementations.RestServiceImpl;

@Component
@RequiredArgsConstructor
public class RestServiceFactory implements ConfigurableObjectFactory<RestServiceImpl, RestServiceConfig> {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final RestClient.Builder restClientBuilder;
  private final ResponseErrorHandler responseErrorHandler;
  private final ClientHttpRequestFactory clientHttpRequestFactory;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public RestServiceImpl create(RestServiceConfig config) {

    RestClient.Builder builder = restClientBuilder.clone();

    builder.requestFactory(clientHttpRequestFactory);

    builder.defaultStatusHandler(responseErrorHandler);

    if (config.getBaseUrl() != null) {
      builder.baseUrl(config.getBaseUrl());
    }

    if (config.getInterceptor() != null) {
      builder.requestInterceptor(config.getInterceptor());
    }

    return new RestServiceImpl(builder.build());
  }

}
