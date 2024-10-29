package th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.dtos.responses.GlobalResponseBody;
import th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.dtos.requests.ScheduleJobRequest;
import th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.entities.Job;

@RequestMapping("/v1/goodmoney-admin-portal/gateway")
public interface JobController {

  String USER_ID_KEY = "USER_ID";

  @PostMapping("/jobs")
  ResponseEntity<GlobalResponseBody<Void>> createJob(
      @Valid @RequestBody ScheduleJobRequest body,
      @RequestAttribute(USER_ID_KEY) String userId);

  @GetMapping("/jobs")
  ResponseEntity<GlobalResponseBody<Page<Job>>> listJobs(@PageableDefault(page = 0, size = 20) Pageable pageable);

  @GetMapping("/jobs/{id}")
  ResponseEntity<GlobalResponseBody<Job>> fetchJobWithId(@PathVariable String id);

  @PutMapping("/jobs/{id}")
  ResponseEntity<GlobalResponseBody<Void>> updateJobWithId(
      @PathVariable String id,
      @Valid @RequestBody ScheduleJobRequest body,
      @RequestAttribute(USER_ID_KEY) String userId);

  @DeleteMapping("/jobs/{id}")
  ResponseEntity<GlobalResponseBody<Void>> removeJobWithId(
      @PathVariable String id,
      @RequestAttribute(USER_ID_KEY) String userId);

}
