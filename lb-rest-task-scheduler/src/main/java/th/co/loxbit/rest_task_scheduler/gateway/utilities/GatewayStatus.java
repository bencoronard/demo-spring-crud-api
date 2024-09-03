package th.co.loxbit.rest_task_scheduler.gateway.utilities;

public enum GatewayStatus {
  
  OPEN("OPEN"),
  CLOSED("CLOSED"),
  UNKNOWN("UNKNOWN");

  private final String status;

  GatewayStatus(String status) {
    this.status = status;
  }

  public String getStatus() {
    return status;
  }

  public static GatewayStatus fromStatus(String status) {
    for (GatewayStatus gatewayStatus : values()) {
      if (gatewayStatus.getStatus().equals(status)) {
        return gatewayStatus;
      }
    }
    throw new IllegalArgumentException("Invalid gateway status: " + status);
  }

}
