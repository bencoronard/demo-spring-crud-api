package th.co.loxbit.adminportal.gateway_scheduler.common.exceptions;

import lombok.Getter;

@Getter
public class WrappableException extends RuntimeException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final int ERROR_CODE;
  private final String DEBUG_MSG;

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public WrappableException(String message, int errorCode, String debugMessage) {
    super(message);
    this.ERROR_CODE = errorCode;
    this.DEBUG_MSG = debugMessage;
  }

}
