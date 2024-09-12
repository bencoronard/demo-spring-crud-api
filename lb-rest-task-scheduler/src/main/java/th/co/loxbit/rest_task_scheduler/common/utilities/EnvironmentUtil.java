package th.co.loxbit.rest_task_scheduler.common.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtil {

  private final boolean isDevEnvironment;

  public EnvironmentUtil(@Value("${app.environment}") String env) {
    this.isDevEnvironment = "dev".equalsIgnoreCase(env);
  }

  public boolean isDevEnvironment() {
    return this.isDevEnvironment;
  }

}
