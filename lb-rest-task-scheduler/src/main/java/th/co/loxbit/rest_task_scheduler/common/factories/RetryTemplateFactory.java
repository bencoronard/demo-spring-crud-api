package th.co.loxbit.rest_task_scheduler.common.factories;

import java.util.List;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.stereotype.Component;

import th.co.loxbit.rest_task_scheduler.common.exceptions.RetryableException;

@Component
public class RetryTemplateFactory {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final List<Class<? extends Throwable>> RETRYABLE_EXCEPTIONS = List.of(RetryableException.class);

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public RetryTemplate withFixedBackOff(int maxAttempts, long backoffMillis) {
    return new RetryTemplateBuilder()
        .maxAttempts(maxAttempts)
        .fixedBackoff(backoffMillis)
        .retryOn(RETRYABLE_EXCEPTIONS)
        .build();
  }

  // ---------------------------------------------------------------------------//

  public RetryTemplate withExponentialBackOff(int maxAttempts, long initInterval, double multiplier, long maxInterval) {
    return new RetryTemplateBuilder()
        .maxAttempts(maxAttempts)
        .exponentialBackoff(initInterval, multiplier, maxInterval)
        .retryOn(RETRYABLE_EXCEPTIONS)
        .build();
  }

}
