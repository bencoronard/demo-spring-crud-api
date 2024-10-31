package th.co.loxbit.adminportal.gateway_scheduler.scheduler.exceptions;

import th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.WrappableException;

public class JobArgumentException extends WrappableException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final int ERROR_CODE = 71;
  private static final String DEBUG_MSG = "Method received illegal or incomplete job arguments";

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public JobArgumentException(String message) {
    super(message, ERROR_CODE, DEBUG_MSG);
  }

}
