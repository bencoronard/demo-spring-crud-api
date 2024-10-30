package th.co.loxbit.adminportal.gateway_scheduler.scheduler.entities;

import java.time.Instant;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.utilities.InstantToEpochSecondsSerializer;

@Builder
@Getter
@Setter
public class Job {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private String id;

  @JsonSerialize(using = InstantToEpochSecondsSerializer.class)
  private Instant start;

  @JsonSerialize(using = InstantToEpochSecondsSerializer.class)
  private Instant end;

  private String message;

  private String initiator;

  private boolean isPartial;

}
