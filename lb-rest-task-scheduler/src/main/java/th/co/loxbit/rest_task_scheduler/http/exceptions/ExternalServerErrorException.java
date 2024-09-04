package th.co.loxbit.rest_task_scheduler.http.exceptions;

import lombok.Builder;
import th.co.loxbit.rest_task_scheduler.common.exceptions.BaseException;

public class ExternalServerErrorException extends BaseException {

  private static final int ERROR_CODE = 44;

  @Builder
  public ExternalServerErrorException(
    int serviceCode,
    int sectionCode,
    String message,
    Throwable cause
  ) {
    super(serviceCode, sectionCode, ERROR_CODE, message, cause);
  }

}


