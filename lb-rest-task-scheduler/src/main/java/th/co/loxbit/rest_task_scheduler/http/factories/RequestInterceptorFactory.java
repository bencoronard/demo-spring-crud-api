package th.co.loxbit.rest_task_scheduler.http.factories;

import org.springframework.stereotype.Component;

import th.co.loxbit.rest_task_scheduler.common.factories.ConfigurableObjectFactory;
import th.co.loxbit.rest_task_scheduler.http.configurers.RequestInterceptorConfigurer;
import th.co.loxbit.rest_task_scheduler.http.interceptors.RequestInterceptor;

@Component
public class RequestInterceptorFactory
    implements ConfigurableObjectFactory<RequestInterceptor, RequestInterceptorConfigurer> {

  @Override
  public RequestInterceptor create(RequestInterceptorConfigurer config) {
    return new RequestInterceptor(config);
  }

}
