package th.co.loxbit.rest_task_scheduler.scheduler.exceptions;

import th.co.loxbit.rest_task_scheduler.common.exceptions.WrappableException;

public class IllegalStartTimeException extends WrappableException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final int ERROR_CODE = 71;
  private static final String RESP_MSG = "Start time must be in the future";

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public IllegalStartTimeException(String message) {
    super(ERROR_CODE, message, RESP_MSG);
  }

}
