package th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.entities;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Job {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private String id;
  private Instant start;
  private Instant end;
  private String message;
  private String initiator;
  private boolean isPartial;

}
