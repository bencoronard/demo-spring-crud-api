package th.co.loxbit.rest_task_scheduler.common.http.configurers;

import org.springframework.http.client.ClientHttpRequestInterceptor;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RestServiceConfigurer {

  private final String baseUrl;
  private final ClientHttpRequestInterceptor interceptor;

}
