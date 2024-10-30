package th.co.loxbit.adminportal.gateway_scheduler.scheduler.services;

import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import th.co.loxbit.adminportal.gateway_scheduler.scheduler.entities.Job;

public interface JobService {

  int SERVICE_CODE = 7000;

  Job scheduleJob(Instant start, Instant end, String message, String initiatedBy);

  Job scheduleJobPartial(Instant end, String initiatedBy);

  Job retrieveJobWithId(String id);

  Page<Job> retrieveAllJobs(Pageable pageable);

  Job rescheduleJob(String id, Instant start, Instant end, String message, String initiatedBy);

  Job descheduleJobWithId(String id);

}
