package th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.services;

import java.time.Instant;
import java.util.List;

import th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.entities.Job;

public interface JobService {

  void scheduleJob(Instant start, Instant end, String message);

  Job retrieveJobWithId(String id);

  List<Job> retrieveAllJobs(int page);

  void rescheduleJob();

  void descheduleJobWithId(String id);

}
