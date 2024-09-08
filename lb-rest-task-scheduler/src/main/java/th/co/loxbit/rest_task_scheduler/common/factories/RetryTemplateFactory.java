package th.co.loxbit.rest_task_scheduler.common.factories;

import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.stereotype.Component;

@Component
public class RetryTemplateFactory {

  public RetryTemplate withFixedBackOff(int maxAttempts, long backoffMillis) {
    return new RetryTemplateBuilder()
        .maxAttempts(maxAttempts)
        .fixedBackoff(backoffMillis)
        .build();
  }

  public RetryTemplate withExponentialBackOff(int maxAttempts, long initInterval, double multiplier, long maxInterval) {
    return new RetryTemplateBuilder()
        .maxAttempts(maxAttempts)
        .exponentialBackoff(initInterval, multiplier, maxInterval)
        .build();
  }

}
