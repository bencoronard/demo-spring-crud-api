package th.co.loxbit.adminportal.gateway_scheduler.common.http.exceptions;

import th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.implementations.RetryableException;

public class RetryableHttpServiceException extends RetryableException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final int ERROR_CODE = 50;
  private static final String DEBUG_MSG = "Unable to perform the requested opertaion at a third-party service";

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public RetryableHttpServiceException(String message) {
    super(message, ERROR_CODE, DEBUG_MSG);
  }

}
