package th.co.loxbit.rest_task_scheduler.common.exceptions;

public class ServiceRuntimeException extends RuntimeException {

  private int serviceCode;
  private int sectionCode;
  private int errorCode;

  public ServiceRuntimeException(int serviceCode, int sectionCode, int errorCode, String message) {
    super(message);
    this.serviceCode = serviceCode;
    this.sectionCode = sectionCode;
    this.errorCode = errorCode;
  }

  public int getRespCode() {
    return this.serviceCode + this.sectionCode + this.errorCode;
  }

  public ServiceRuntimeException merge(ServiceRuntimeException that) {
    this.serviceCode = this.serviceCode + that.serviceCode / 100;
    this.sectionCode = this.sectionCode + that.sectionCode / 100;
    this.errorCode = that.errorCode;
    return this;
  }

}
