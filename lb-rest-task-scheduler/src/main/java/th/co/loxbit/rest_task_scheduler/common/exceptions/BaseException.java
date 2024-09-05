package th.co.loxbit.rest_task_scheduler.common.exceptions;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {

  private final int serviceCode;
  private final int sectionCode;
  private final int errorCode;

  public BaseException(
      int serviceCode,
      int sectionCode,
      int errorCode,
      String message,
      Throwable cause) {
    super(message, cause);
    this.serviceCode = serviceCode;
    this.sectionCode = sectionCode;
    this.errorCode = errorCode;
  }

  public String getRespCode() {
    int respCode = this.serviceCode + this.sectionCode + this.errorCode;
    return Integer.toString(respCode);
  }

}
