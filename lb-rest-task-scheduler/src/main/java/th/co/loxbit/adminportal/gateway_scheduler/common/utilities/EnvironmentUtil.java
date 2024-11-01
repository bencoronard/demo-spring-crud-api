package th.co.loxbit.adminportal.gateway_scheduler.common.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtil {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final boolean isDev;

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public EnvironmentUtil(@Value("${info.app.environment}") String env) {
    this.isDev = "dev".equalsIgnoreCase(env);
  }

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public boolean isDev() {
    return this.isDev;
  }

}
