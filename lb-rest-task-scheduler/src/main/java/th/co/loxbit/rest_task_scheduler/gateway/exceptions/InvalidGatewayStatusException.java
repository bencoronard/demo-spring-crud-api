package th.co.loxbit.rest_task_scheduler.gateway.exceptions;

import lombok.Builder;
import th.co.loxbit.rest_task_scheduler.common.exceptions.BaseException;

public class InvalidGatewayStatusException extends BaseException {

  private static final int ERROR_CODE = 10;

  @Builder
  public InvalidGatewayStatusException(
      int serviceCode,
      int sectionCode,
      String message,
      Throwable cause) {
    super(serviceCode, sectionCode, ERROR_CODE, message, cause);
  }

}
