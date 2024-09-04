package th.co.loxbit.rest_task_scheduler.common.exceptions;

import lombok.Builder;

public class CatchAllException extends BaseException {

  private static final int ERROR_CODE = 0;

  @Builder
  public CatchAllException(
    int serviceCode,
    int sectionCode,
    int errorCode,
    String message,
    Throwable cause
  ) {
    super(serviceCode, sectionCode, ERROR_CODE, message, cause);
  }

}
