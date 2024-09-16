package th.co.loxbit.rest_task_scheduler.scheduler.entities;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.Setter;
import th.co.loxbit.rest_task_scheduler.gateway.services.GatewayService;

@Setter
public class CloseGatewayTask extends QuartzJobBean {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private GatewayService gatewayService;
  private String message;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  protected void executeInternal(@NonNull JobExecutionContext context) throws JobExecutionException {

    this.gatewayService.closeGateway(message, message);

  }

}
