package th.co.loxbit.adminportal.gateway_scheduler.scheduler.exceptions;

import th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.WrappableException;

public class IllegalStartTimeException extends WrappableException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final int ERROR_CODE = 71;
  private static final String RESP_MSG = "Invalid start time";

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public IllegalStartTimeException(String message) {
    super(ERROR_CODE, message, RESP_MSG);
  }

}
