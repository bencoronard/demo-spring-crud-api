package th.co.loxbit.adminportal.gateway_scheduler.scheduler.dtos;

import java.time.Instant;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Getter;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.utilities.InstantToEpochSecondsSerializer;

@Builder
@Getter
public class GatewaySchedule {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String id;

  @JsonSerialize(using = InstantToEpochSecondsSerializer.class)
  private final Instant start;

  @JsonSerialize(using = InstantToEpochSecondsSerializer.class)
  private final Instant end;

  private final String message;

  private final String creator;

  private final Boolean partial;

}
