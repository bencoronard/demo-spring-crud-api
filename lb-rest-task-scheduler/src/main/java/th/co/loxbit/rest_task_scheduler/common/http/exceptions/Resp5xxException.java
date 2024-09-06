package th.co.loxbit.rest_task_scheduler.common.http.exceptions;

import lombok.Builder;
import th.co.loxbit.rest_task_scheduler.common.exceptions.ServiceRuntimeException;

public class Resp5xxException extends ServiceRuntimeException {

  private static final int ERROR_CODE = 50;

  @Builder
  public Resp5xxException(int serviceCode, int sectionCode, String message) {
    super(serviceCode, sectionCode, ERROR_CODE, message);
  }

}
