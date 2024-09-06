package th.co.loxbit.rest_task_scheduler.gateway.exceptions;

import lombok.Builder;
import th.co.loxbit.rest_task_scheduler.common.exceptions.ServiceRuntimeException;

public class InvalidGatewayStatusException extends ServiceRuntimeException {

  private static final int ERROR_CODE = 10;

  @Builder
  public InvalidGatewayStatusException(int serviceCode, int sectionCode, String message) {
    super(serviceCode, sectionCode, ERROR_CODE, message);
  }

}
