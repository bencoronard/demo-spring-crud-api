package th.co.loxbit.adminportal.gateway_scheduler.scheduler.exceptions;

import th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.WrappingException;

public class TaskExecutionException extends RuntimeException {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public TaskExecutionException(WrappingException cause) {
    super(cause);
  }

}
