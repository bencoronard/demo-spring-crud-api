package th.co.moneydd.adminportal.gateway_scheduler.scheduler.entities;

import org.quartz.JobDetail;
import org.quartz.Trigger;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GatewayJob {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String id;
  private final JobDetail closeTaskDetail;
  private final JobDetail openTaskDetail;
  private final Trigger closeTaskTrigger;
  private final Trigger openTaskTrigger;

}
