package th.co.loxbit.adminportal.gateway_scheduler.scheduler.controller;

import java.util.List;

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
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.dtos.GatewaySchedule;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.dtos.requests.ScheduleJobRequest;

@RequestMapping("/v1/goodmoney-admin-portal/gateway")
public interface JobSchedulingController {

  String USER_ID_KEY = "USER_ID";

  @GetMapping("/jobs")
  ResponseEntity<GlobalResponseBody<List<GatewaySchedule>>> getJobSchedules();

  @GetMapping("/jobs/{id}")
  ResponseEntity<GlobalResponseBody<GatewaySchedule>> getJobSchedule(@PathVariable String id);

  @PostMapping("/jobs")
  ResponseEntity<GlobalResponseBody<Void>> createJobSchedule(@Valid @RequestBody ScheduleJobRequest requestBody,
      @RequestAttribute(USER_ID_KEY) String userId);

  @PutMapping("/jobs/{id}")
  ResponseEntity<GlobalResponseBody<Void>> updateJobSchedule(@PathVariable String id,
      @Valid @RequestBody ScheduleJobRequest requestBody,
      @RequestAttribute(USER_ID_KEY) String userId);

  @DeleteMapping("/jobs/{id}")
  ResponseEntity<GlobalResponseBody<Void>> deleteJobSchedule(@PathVariable String id,
      @RequestAttribute(USER_ID_KEY) String userId);

}
