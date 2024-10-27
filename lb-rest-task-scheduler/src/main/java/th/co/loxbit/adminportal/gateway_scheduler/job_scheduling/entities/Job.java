package th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.entities;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Job {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String id;
  private final Instant start;
  private final Instant end;
  private final String message;

}
