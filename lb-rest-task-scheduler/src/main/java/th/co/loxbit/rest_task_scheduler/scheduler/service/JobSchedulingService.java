package th.co.loxbit.rest_task_scheduler.scheduler.service;

public interface JobSchedulingService {

  int SERVICE_CODE = 5000;

  void getScheduledJobs();

  void scheduleJob();

  void updateJob(String jobId);

  void descheduleJob(String jobId);

}
