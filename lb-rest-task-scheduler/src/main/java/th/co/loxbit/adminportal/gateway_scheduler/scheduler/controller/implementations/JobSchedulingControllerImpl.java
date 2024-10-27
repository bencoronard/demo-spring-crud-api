package th.co.loxbit.adminportal.gateway_scheduler.scheduler.controller.implementations;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.dtos.responses.GlobalResponseBody;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.utilities.ResponseBodyUtils;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.controller.JobSchedulingController;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.dtos.GatewaySchedule;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.dtos.requests.ScheduleJobRequest;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.service.JobSchedulingService;

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
  public ResponseEntity<GlobalResponseBody<List<GatewaySchedule>>> getJobSchedules() {

    List<GatewaySchedule> data = jobSchedulingService.getJobSchedules();

    GlobalResponseBody<List<GatewaySchedule>> responseBody = ResponseBodyUtils
        .createSuccessResponseBody("scheduled jobs", data);

    HttpHeaders headers = addNoCacheHeaders();

    return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<GatewaySchedule>> getJobSchedule(String id) {

    GatewaySchedule data = jobSchedulingService.getJobScheduleWithId(id);

    GlobalResponseBody<GatewaySchedule> responseBody = ResponseBodyUtils
        .createSuccessResponseBody("scheduled job", data);

    HttpHeaders headers = addNoCacheHeaders();

    return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> createJobSchedule(@Valid ScheduleJobRequest requestBody,
      String userId) {

    jobSchedulingService.createJobSchedule(
        Instant.ofEpochSecond(requestBody.start()),
        Instant.ofEpochSecond(requestBody.end()),
        requestBody.message(),
        userId);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody("job scheduled", null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> updateJobSchedule(String id,
      @Valid ScheduleJobRequest requestBody,
      String userId) {

    jobSchedulingService.updateJobScheduleWithId(
        id,
        Instant.ofEpochSecond(requestBody.start()),
        Instant.ofEpochSecond(requestBody.end()),
        requestBody.message(),
        userId);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody("job updated", null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> deleteJobSchedule(String id, String userId) {

    jobSchedulingService.deleteJobScheduleWithId(id, userId);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody("job deleted", null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//
  // Utilities
  // ---------------------------------------------------------------------------//

  private HttpHeaders addNoCacheHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setCacheControl("no-store, no-cache, must-revalidate, max-age=0");
    headers.setPragma("no-cache");
    headers.setExpires(0);
    return headers;
  }

}
