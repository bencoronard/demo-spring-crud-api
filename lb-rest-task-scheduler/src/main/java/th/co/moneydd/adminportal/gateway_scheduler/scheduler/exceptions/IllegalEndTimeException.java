package th.co.moneydd.adminportal.gateway_scheduler.scheduler.exceptions;

import th.co.moneydd.adminportal.gateway_scheduler.common.exceptions.WrappableException;

public class IllegalEndTimeException extends WrappableException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final int ERROR_CODE = 72;
  private static final String RESP_MSG = "Invalid end time";

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public IllegalEndTimeException(String message) {
    super(ERROR_CODE, message, RESP_MSG);
  }

}
