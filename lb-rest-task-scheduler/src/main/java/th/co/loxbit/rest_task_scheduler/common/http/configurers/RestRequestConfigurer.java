package th.co.loxbit.rest_task_scheduler.common.http.configurers;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RestRequestConfigurer {

  private final int maxAttempts;
  private final int backOffInSeconds;

}
