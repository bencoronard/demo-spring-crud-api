package th.co.loxbit.rest_task_scheduler.http.exceptions;

import lombok.Builder;
import th.co.loxbit.rest_task_scheduler.common.exceptions.BaseException;

public class ExternalServerErrorException extends BaseException {

  @Builder
  public ExternalServerErrorException(int serviceCode, int sectionCode, int errorCode, String message) {
    super(serviceCode, sectionCode, errorCode, message);
  }

}


