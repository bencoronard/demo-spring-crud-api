package th.co.loxbit.rest_task_scheduler.common.http.exceptions;

import lombok.Builder;
import th.co.loxbit.rest_task_scheduler.common.exceptions.ServiceRuntimeException;

public class Resp4xxException extends ServiceRuntimeException {

  private static final int ERROR_CODE = 40;

  @Builder
  public Resp4xxException(int serviceCode, int sectionCode, String message) {
    super(serviceCode, sectionCode, ERROR_CODE, message);
  }

}
