package th.co.loxbit.rest_task_scheduler.common.http.exceptions;

import th.co.loxbit.rest_task_scheduler.common.exceptions.implementations.RetryableException;

public class RetryableHttpServiceException extends RetryableException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final int ERROR_CODE = 50;
  private static final String RESP_MSG = "Unable to perform the requested opertaion at a third-party service";

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public RetryableHttpServiceException(String message) {
    super(ERROR_CODE, message, RESP_MSG);
  }

}
