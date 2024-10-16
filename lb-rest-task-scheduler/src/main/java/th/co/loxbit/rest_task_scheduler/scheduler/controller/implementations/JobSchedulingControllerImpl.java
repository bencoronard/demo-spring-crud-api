package th.co.loxbit.rest_task_scheduler.scheduler.controller.implementations;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.common.http.dtos.responses.GlobalResponseBody;
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
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<List<GatewaySchedule>>> getScheduledJobs() {

    List<GatewaySchedule> data = jobSchedulingService.getScheduledJobs();

    GlobalResponseBody<List<GatewaySchedule>> responseBody = ResponseBodyUtils
        .createSuccessResponseBody("scheduled jobs", data);

    HttpHeaders headers = new HttpHeaders();
    headers.setCacheControl("no-store, no-cache, must-revalidate, max-age=0");
    headers.setPragma("no-cache");
    headers.setExpires(0);

    return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> scheduleJob(@Valid ScheduleJobRequest requestBody, String userId) {

    jobSchedulingService.scheduleJob(requestBody.start(), requestBody.end(), requestBody.message(), userId);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody(null, null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> updateJob(String id, @Valid ScheduleJobRequest requestBody,
      String userId) {

    jobSchedulingService.updateJob(id, requestBody.start(), requestBody.end(), requestBody.message(), userId);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody(null, null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> descheduleJob(String id, String userId) {

    jobSchedulingService.descheduleJob(id, userId);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody(null, null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

}
