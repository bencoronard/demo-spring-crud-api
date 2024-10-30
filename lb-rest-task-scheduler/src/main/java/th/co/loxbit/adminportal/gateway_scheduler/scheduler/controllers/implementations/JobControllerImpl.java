package th.co.loxbit.adminportal.gateway_scheduler.scheduler.controllers.implementations;

import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import th.co.loxbit.adminportal.gateway_scheduler.audit.entities.AuditRecordType;
import th.co.loxbit.adminportal.gateway_scheduler.audit.service.AuditRecordService;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.dtos.responses.GlobalResponseBody;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.utilities.ResponseBodyUtils;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.controllers.JobController;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.dtos.requests.ScheduleJobRequest;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.entities.Job;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.services.JobService;

@RestController
@RequiredArgsConstructor
public class JobControllerImpl implements JobController {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final JobService jobService;
  private final AuditRecordService auditRecordService;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> createJob(@Valid ScheduleJobRequest requestBody, String userId) {

    Instant start = Instant.ofEpochSecond(requestBody.start());
    Instant end = Instant.ofEpochSecond(requestBody.end());

    Job createdJob = jobService.scheduleJob(
        start,
        end,
        requestBody.message(),
        userId);

    auditRecordService.record(createdJob.getId(), start, end, userId, AuditRecordType.CREATE);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody("Job scheduled", null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Page<Job>>> listJobs(Pageable pageable) {

    Page<Job> jobPage = jobService.retrieveAllJobs(pageable);

    GlobalResponseBody<Page<Job>> responseBody = ResponseBodyUtils.createSuccessResponseBody("Scheduled jobs", jobPage);

    HttpHeaders headers = addNoCacheHeaders();

    return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Job>> fetchJobWithId(String id) {

    Job job = jobService.retrieveJobWithId(id);

    GlobalResponseBody<Job> responseBody = ResponseBodyUtils.createSuccessResponseBody("Scheduled job", job);

    HttpHeaders headers = addNoCacheHeaders();

    return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> updateJobWithId(String id, @Valid ScheduleJobRequest requestBody,
      String userId) {

    Instant start = Instant.ofEpochSecond(requestBody.start());
    Instant end = Instant.ofEpochSecond(requestBody.end());

    Job updatedJob = jobService.rescheduleJob(id, start, end, requestBody.message(), userId);

    auditRecordService.record(updatedJob.getId(), start, end, userId, AuditRecordType.EDIT);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody("Job updated", null);

    return new ResponseEntity<>(responseBody, HttpStatus.OK);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public ResponseEntity<GlobalResponseBody<Void>> removeJobWithId(String id, String userId) {

    Job deletedJob = jobService.descheduleJobWithId(userId);

    auditRecordService.record(deletedJob.getId(), deletedJob.getStart(), deletedJob.getEnd(), userId,
        AuditRecordType.DELETE);

    GlobalResponseBody<Void> responseBody = ResponseBodyUtils.createSuccessResponseBody("Job deleted", null);

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
