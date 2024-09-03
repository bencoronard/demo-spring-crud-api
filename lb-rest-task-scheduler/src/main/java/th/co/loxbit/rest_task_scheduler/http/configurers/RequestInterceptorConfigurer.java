package th.co.loxbit.rest_task_scheduler.http.configurers;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestInterceptorConfigurer {

  private final String apiKey;

}
