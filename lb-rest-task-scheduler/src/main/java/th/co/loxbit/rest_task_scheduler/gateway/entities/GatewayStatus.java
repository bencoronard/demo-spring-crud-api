package th.co.loxbit.rest_task_scheduler.gateway.entities;

public enum GatewayStatus {

  OPEN("OPEN"),
  CLOSED("CLOSED");

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String status;

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  GatewayStatus(String status) {
    this.status = status;
  }

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public String getStatus() {
    return status;
  }

  // ---------------------------------------------------------------------------//

  public static GatewayStatus fromStatus(String status) {
    for (GatewayStatus gatewayStatus : values()) {
      if (gatewayStatus.getStatus().equals(status)) {
        return gatewayStatus;
      }
    }
    throw new IllegalArgumentException("Invalid gateway status: " + status);
  }

}
