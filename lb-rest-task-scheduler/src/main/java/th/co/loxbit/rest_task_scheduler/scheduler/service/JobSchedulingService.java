package th.co.loxbit.rest_task_scheduler.scheduler.service;

import java.util.List;

import org.quartz.SchedulerException;

import th.co.loxbit.rest_task_scheduler.scheduler.entities.GatewaySchedule;

public interface JobSchedulingService {

  int SERVICE_CODE = 7000;

  void scheduleJob(int start, int end, String message, String owner) throws SchedulerException;

  void descheduleJob(String jobId) throws SchedulerException;

  List<GatewaySchedule> getScheduledJobs() throws SchedulerException;

  void updateJob(String jobId, int newStart, int newEnd, String newMessage, String newOwner) throws SchedulerException;

}
