package th.co.loxbit.rest_task_scheduler.scheduler.entities;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GatewaySchedule {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String id;
  private final Instant start;
  private final Instant end;
  private final String owner;

}
