package th.co.loxbit.adminportal.gateway_scheduler.gateway.entities;

import lombok.Getter;

public enum GatewayStatus {

  OPEN("OPEN"),
  CLOSED("CLOSED");

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  @Getter
  private final String status;

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  GatewayStatus(String status) {
    this.status = status;
  }

}
