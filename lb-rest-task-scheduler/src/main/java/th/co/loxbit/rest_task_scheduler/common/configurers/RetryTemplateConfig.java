package th.co.loxbit.rest_task_scheduler.common.configurers;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RetryTemplateConfig {

  private final int maxAttempts;

}
