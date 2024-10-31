package th.co.loxbit.adminportal.gateway_scheduler.scheduler.exceptions;

import th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.WrappableException;

public class JobNotDeletableException extends WrappableException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final int ERROR_CODE = 71;
  private static final String DEBUG_MSG = "Partial job deletion attempted.";

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public JobNotDeletableException(String message) {
    super(message, ERROR_CODE, DEBUG_MSG);
  }

}
