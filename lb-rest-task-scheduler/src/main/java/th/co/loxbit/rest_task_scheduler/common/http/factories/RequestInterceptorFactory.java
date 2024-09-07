package th.co.loxbit.rest_task_scheduler.common.http.factories;

import org.springframework.stereotype.Component;

import th.co.loxbit.rest_task_scheduler.common.factories.ConfigurableObjectFactory;
import th.co.loxbit.rest_task_scheduler.common.http.configurers.RequestInterceptorConfig;
import th.co.loxbit.rest_task_scheduler.common.http.interceptors.RequestInterceptor;

@Component
public class RequestInterceptorFactory
    implements ConfigurableObjectFactory<RequestInterceptor, RequestInterceptorConfig> {

  @Override
  public RequestInterceptor create(RequestInterceptorConfig config) {
    return new RequestInterceptor(config);
  }

}
