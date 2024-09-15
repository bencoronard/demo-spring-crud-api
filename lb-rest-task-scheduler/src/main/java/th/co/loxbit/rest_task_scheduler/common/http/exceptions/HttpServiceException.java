package th.co.loxbit.rest_task_scheduler.common.http.exceptions;

import th.co.loxbit.rest_task_scheduler.common.exceptions.WrappableException;

public class HttpServiceException extends WrappableException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final int ERROR_CODE = 40;
  private static final String RESP_MSG = "Unable to perform the requested opertaion at the server side";

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public HttpServiceException(String message) {
    super(ERROR_CODE, message, RESP_MSG);
  }

}
