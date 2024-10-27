package th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.repositories.Implementations;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.entities.Job;
import th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.repositories.JobRepository;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.entities.tasks.CloseGatewayTask;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.entities.tasks.OpenGatewayTask;

@Component
@RequiredArgsConstructor
public class JobRepositoryQuartz implements JobRepository {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final Scheduler scheduler;

  private final String JDATA_OWNER_KEY = "Hello, world";
  private final String JDATA_MESSAGE_KEY = "Hello, world";
  private final String JDATA_JOB_START_KEY = "Hello, world";
  private final String JDATA_JOB_END_KEY = "Hello, world";
  private final String TASK_CLOSE_KEY = "Hello, world";
  private final String TASK_OPEN_KEY = "Hello, world";

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public Job save(Job job) {
    try {

      Optional<String> existingId = Optional.ofNullable(job.getId());

      if (existingId.isPresent()) {
        // delete existing job
      } else {
        job.setId(UUID.randomUUID().toString());
      }

      String id = job.getId();
      Instant start = job.getStart();
      Instant end = job.getEnd();

      Map<String, Object> jobData = new HashMap<>();
      jobData.put(JDATA_JOB_START_KEY, start);
      jobData.put(JDATA_JOB_END_KEY, end);
      jobData.put(JDATA_MESSAGE_KEY, job.getMessage());
      jobData.put(JDATA_OWNER_KEY, job.getOwner());

      scheduleOneTimeJob(OpenGatewayTask.class, TASK_OPEN_KEY, id, end, jobData);
      scheduleOneTimeJob(CloseGatewayTask.class, TASK_CLOSE_KEY, id, start, jobData);

      return job;

    } catch (SchedulerException e) {
      throw new RuntimeException();
    }

  }

  // ---------------------------------------------------------------------------//

  @Override
  public Optional<Job> findById(String id) {
    try {

      Optional<Trigger> taskCloseTrigger = Optional
          .ofNullable(scheduler.getTrigger(new TriggerKey(TASK_CLOSE_KEY, id)));
      Optional<Trigger> taskOpenTrigger = Optional
          .ofNullable(scheduler.getTrigger(new TriggerKey(TASK_OPEN_KEY, id)));

      if (!taskCloseTrigger.isPresent() && !taskOpenTrigger.isPresent()) {
        return Optional.empty();
      }

      Job.JobBuilder builder = Job.builder();
      JobDataMap jobData = scheduler.getJobDetail(taskOpenTrigger.get().getJobKey()).getJobDataMap();

      builder.id(id);
      builder.start((Instant) jobData.get(JDATA_JOB_START_KEY));
      builder.end(taskOpenTrigger.get().getStartTime().toInstant());
      builder.message((String) jobData.get(JDATA_MESSAGE_KEY));
      builder.owner((String) jobData.getString(JDATA_OWNER_KEY));
      builder.isPartial(taskCloseTrigger.isPresent() ? true : false);

      return Optional.of(builder.build());

    } catch (SchedulerException e) {
      throw new RuntimeException();
    }
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Page<Job> findAll(Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void delete(Job job) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  // ---------------------------------------------------------------------------//
  // Utilities
  // ---------------------------------------------------------------------------//

  private void scheduleOneTimeJob(
      Class<? extends QuartzJobBean> jobClass,
      String taskKey,
      String jobId,
      Instant fireAt,
      Map<String, Object> jobData) throws SchedulerException {

    JobDetail taskDetail = JobBuilder.newJob(jobClass)
        .withIdentity(taskKey, jobId)
        .usingJobData(new JobDataMap(jobData))
        .build();

    Trigger taskTrigger = TriggerBuilder.newTrigger()
        .withIdentity(taskKey, jobId)
        .startAt(Date.from(fireAt))
        .withSchedule(SimpleScheduleBuilder
            .simpleSchedule()
            .withRepeatCount(0)
            .withMisfireHandlingInstructionIgnoreMisfires())
        .build();

    scheduler.scheduleJob(taskDetail, taskTrigger);
  }

}
