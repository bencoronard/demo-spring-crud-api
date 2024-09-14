package th.co.loxbit.rest_task_scheduler.scheduler.controller.implementations;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.common.http.responses.GlobalResponseBody;
import th.co.loxbit.rest_task_scheduler.common.http.utilities.ResponseBodyUtils;
import th.co.loxbit.rest_task_scheduler.scheduler.controller.JobSchedulingController;
import th.co.loxbit.rest_task_scheduler.scheduler.dtos.requests.ScheduleJobRequest;
import th.co.loxbit.rest_task_scheduler.scheduler.entities.GatewaySchedule;
import th.co.loxbit.rest_task_scheduler.scheduler.service.JobSchedulingService;

@RestController
@RequiredArgsConstructor
public class JobSchedulingControllerImpl implements JobSchedulingController {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final JobSchedulingService jobSchedulingService;

  // ---------------------------------------------------------------------------//
  // Functions
  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<List<GatewaySchedule>>> getScheduledJobs() throws SchedulerException {

    List<GatewaySchedule> data = jobSchedulingService.getScheduledJobs();

    GlobalResponseBody<List<GatewaySchedule>> responseBody = ResponseBodyUtils
        .createSuccessResponseBody("scheduled job", data);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> scheduleJob(@Valid ScheduleJobRequest requestBody)
      throws SchedulerException {

    jobSchedulingService.scheduleJob(requestBody.start(), requestBody.end(), requestBody.message(),
        requestBody.userId());

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody(null, null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> updateJob(String id, @Valid ScheduleJobRequest requestBody)
      throws SchedulerException {

    jobSchedulingService.updateJob(id, requestBody.start(), requestBody.end(), requestBody.message(),
        requestBody.userId());

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody(null, null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> descheduleJob(String id) throws SchedulerException {

    jobSchedulingService.descheduleJob(id);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody(null, null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

}
