package th.co.moneydd.adminportal.gateway_scheduler.scheduler.exceptions;

import th.co.moneydd.adminportal.gateway_scheduler.common.exceptions.WrappingException;

public class JobExecutionException extends RuntimeException {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public JobExecutionException(WrappingException cause) {
    super(cause);
  }

}
