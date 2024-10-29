package th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.services;

import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.entities.Job;

public interface JobService {

  void scheduleJob(Instant start, Instant end, String message, String initiator);

  void scheduleJobPartial(Instant end, String initiator);

  Job retrieveJobWithId(String id);

  Page<Job> retrieveAllJobs(Pageable pageable);

  void rescheduleJob(String id, Instant start, Instant end, String message, String initiator);

  void descheduleJobWithId(String id);

}
