package th.co.loxbit.rest_task_scheduler.scheduler.exceptions;

import th.co.loxbit.rest_task_scheduler.common.exceptions.WrappableException;

public class IllegalEndTimeException extends WrappableException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final int ERROR_CODE = 72;
  private static final String RESP_MSG = "End time must be after start time";

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public IllegalEndTimeException(String message) {
    super(ERROR_CODE, message, RESP_MSG);
  }

}
