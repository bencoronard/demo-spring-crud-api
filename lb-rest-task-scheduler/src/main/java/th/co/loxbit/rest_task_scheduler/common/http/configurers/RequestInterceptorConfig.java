package th.co.loxbit.rest_task_scheduler.common.http.configurers;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestInterceptorConfig {

  private final String apiKey;

}
