package th.co.moneydd.adminportal.gateway_scheduler.scheduler.service;

import java.time.Instant;
import java.util.List;

import th.co.moneydd.adminportal.gateway_scheduler.scheduler.dtos.GatewaySchedule;

public interface JobSchedulingService {

  int SERVICE_CODE = 7000;

  List<GatewaySchedule> getJobSchedules();

  GatewaySchedule getJobScheduleWithId(String jobId);

  void createJobSchedule(Instant start, Instant end, String message, String createdBy);

  void createPartialJobSchedule(Instant start, Instant end, String message, String createdBy);

  void deleteJobScheduleWithId(String jobId, String deletedBy);

  void updateJobScheduleWithId(String jobId, Instant newStart, Instant newEnd, String newMessage, String updatedBy);

}
