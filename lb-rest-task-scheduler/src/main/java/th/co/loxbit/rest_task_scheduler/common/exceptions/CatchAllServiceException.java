package th.co.loxbit.rest_task_scheduler.common.exceptions;

import lombok.Builder;

public class CatchAllServiceException extends ServiceRuntimeException {

  @Builder
  public CatchAllServiceException(int serviceCode, int sectionCode, int errorCode, String message) {
    super(serviceCode, sectionCode, errorCode, message);
  }

}
