package th.co.loxbit.rest_task_scheduler.common.exceptions.implementations;

import th.co.loxbit.rest_task_scheduler.common.exceptions.WrappableException;

public class RetryableException extends WrappableException {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public RetryableException(int errorCode, String message, String respMessage) {
    super(errorCode, message, respMessage);
  }

}
