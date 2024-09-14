package th.co.loxbit.rest_task_scheduler.scheduler.service;

import java.util.List;

import th.co.loxbit.rest_task_scheduler.scheduler.entities.GatewaySchedule;

public interface JobSchedulingService {

  int SERVICE_CODE = 7000;

  void scheduleJob(int start, int end, String message, String owner);

  void descheduleJob(String jobId);

  List<GatewaySchedule> getScheduledJobs();

  void updateJob(String jobId, int newStart, int newEnd, String newMessage, String newOwner);

}
