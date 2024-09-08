package th.co.loxbit.rest_task_scheduler.common.factories.implementations;

import org.springframework.retry.support.RetryTemplateBuilder;
import org.springframework.stereotype.Component;

@Component
public class RetryTemplateBuilderFactory {

  public static RetryTemplateBuilder create() {
    return new RetryTemplateBuilder();
  }

}
