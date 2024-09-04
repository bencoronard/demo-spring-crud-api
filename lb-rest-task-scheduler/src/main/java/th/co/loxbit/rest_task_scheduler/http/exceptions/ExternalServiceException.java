package th.co.loxbit.rest_task_scheduler.http.exceptions;

import lombok.Builder;
import th.co.loxbit.rest_task_scheduler.common.exceptions.BaseException;

public class ExternalServiceException extends BaseException {

  private static final int ERROR_CODE = 44;

  @Builder
  public ExternalServiceException(
    int serviceCode,
    int sectionCode,
    String message,
    Throwable cause
  ) {
    super(serviceCode, sectionCode, ERROR_CODE, message, cause);
  }

}


