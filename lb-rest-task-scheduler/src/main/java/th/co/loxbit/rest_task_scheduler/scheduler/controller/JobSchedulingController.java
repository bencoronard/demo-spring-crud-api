package th.co.loxbit.rest_task_scheduler.scheduler.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import th.co.loxbit.rest_task_scheduler.common.http.responses.GlobalResponseBody;
import th.co.loxbit.rest_task_scheduler.scheduler.dtos.requests.ScheduleJobRequest;
import th.co.loxbit.rest_task_scheduler.scheduler.entities.GatewaySchedule;

@RestController
@RequestMapping("/jobs")
public interface JobSchedulingController {

  @GetMapping
  ResponseEntity<GlobalResponseBody<List<GatewaySchedule>>> getScheduledJobs();

  @PostMapping
  ResponseEntity<GlobalResponseBody<Void>> scheduleJob(@Valid @RequestBody ScheduleJobRequest requestBody);

  @PutMapping("/{id}")
  ResponseEntity<GlobalResponseBody<Void>> updateJob(@PathVariable String id,
      @Valid @RequestBody ScheduleJobRequest requestBody);

  @DeleteMapping("/{id}")
  ResponseEntity<GlobalResponseBody<Void>> descheduleJob(@PathVariable String id);

}
