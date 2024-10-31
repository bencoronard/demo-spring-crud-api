package th.co.loxbit.adminportal.gateway_scheduler.scheduler.exceptions;

import th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.WrappableException;

public class JobNotFoundException extends WrappableException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final int ERROR_CODE = 73;
  private static final String DEBUG_MSG = "Job with the given ID not found";

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public JobNotFoundException(String message) {
    super(message, ERROR_CODE, DEBUG_MSG);
  }

}
