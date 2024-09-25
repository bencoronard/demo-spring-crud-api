package th.co.loxbit.rest_task_scheduler.scheduler.entities.tasks;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.gateway.services.GatewayService;

@Component
@Scope("prototype")
@RequiredArgsConstructor
public class OpenGatewayTask extends QuartzJobBean {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final GatewayService gatewayService;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  protected void executeInternal(@NonNull JobExecutionContext context) throws JobExecutionException {

    this.gatewayService.openGateway();

  }

}
