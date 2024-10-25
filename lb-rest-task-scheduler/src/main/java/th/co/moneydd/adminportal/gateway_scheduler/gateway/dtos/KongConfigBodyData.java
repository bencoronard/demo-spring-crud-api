package th.co.moneydd.adminportal.gateway_scheduler.gateway.dtos;

import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Getter;
import th.co.moneydd.adminportal.gateway_scheduler.common.utilities.InstantToIsoStringSerializer;

@Builder
@Getter
public class KongConfigBodyData {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  @JsonProperty("app_maintenance")
  private final Boolean appMaintenance;

  @JsonProperty("app_maintenance_message")
  private final String appMaintenanceMsg;

  @JsonProperty("app_maintenance_start_datetime")
  @JsonSerialize(using = InstantToIsoStringSerializer.class)
  private final Instant appMaintenanceStart;

  @JsonProperty("app_maintenance_end_datetime")
  @JsonSerialize(using = InstantToIsoStringSerializer.class)
  private final Instant appMaintenanceEnd;

}
