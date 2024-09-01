package th.co.loxbit.rest_task_scheduler.modules.common.entities.http;

public enum OperationCode {
  
  SUCCESS("0000"),
  CLIENT_ERROR("1000"),
  SERVER_ERROR("2000"),
  UNKNOWN_ERROR("4000");

  private final String code;

  OperationCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public static OperationCode fromCode(String code) {
    for (OperationCode operationCode : values()) {
      if (operationCode.getCode().equals(code)) {
        return operationCode;
      }
    }
    throw new IllegalArgumentException("Invalid operation code: " + code);
  }
  
}
