package th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.implementations;

import th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.WrappableException;

public class RetryableException extends WrappableException {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public RetryableException(String message, int errorCode, String debugMessage) {
    super(message, errorCode, debugMessage);
  }

}
