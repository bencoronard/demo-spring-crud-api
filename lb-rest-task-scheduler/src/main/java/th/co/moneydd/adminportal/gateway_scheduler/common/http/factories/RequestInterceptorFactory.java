package th.co.moneydd.adminportal.gateway_scheduler.common.http.factories;

import org.springframework.stereotype.Component;

import th.co.moneydd.adminportal.gateway_scheduler.common.factories.ConfigurableObjectFactory;
import th.co.moneydd.adminportal.gateway_scheduler.common.http.configurers.RequestInterceptorConfig;
import th.co.moneydd.adminportal.gateway_scheduler.common.http.interceptors.RequestInterceptor;

@Component
public class RequestInterceptorFactory
    implements ConfigurableObjectFactory<RequestInterceptor, RequestInterceptorConfig> {

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public RequestInterceptor create(RequestInterceptorConfig config) {
    return new RequestInterceptor(config);
  }

}
