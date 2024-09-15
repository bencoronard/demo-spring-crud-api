package th.co.loxbit.rest_task_scheduler.common.exceptions;

import lombok.Getter;

@Getter
public class WrappableException extends RuntimeException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final int ERROR_CODE;
  private final String RESP_MSG;

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public WrappableException(int errorCode, String message, String respMessage) {
    super(message);
    this.ERROR_CODE = errorCode;
    this.RESP_MSG = respMessage;
  }

}
