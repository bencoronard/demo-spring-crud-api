package th.co.loxbit.adminportal.gateway_scheduler.common.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtil {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final boolean isDevEnvironment;

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public EnvironmentUtil(@Value("${info.app.environment}") String env) {
    this.isDevEnvironment = "dev".equalsIgnoreCase(env);
  }

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public boolean isDevEnvironment() {
    return this.isDevEnvironment;
  }

}
