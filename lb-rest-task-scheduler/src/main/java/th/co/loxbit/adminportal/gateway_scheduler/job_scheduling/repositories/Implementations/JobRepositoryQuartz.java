package th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.repositories.Implementations;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

  private final String JDATA_OWNER_KEY = "owner";
  private final String JDATA_MESSAGE_KEY = "message";
  private final String JDATA_JOB_START_KEY = "start";
  private final String JDATA_JOB_END_KEY = "end";
  private final String TASK_CLOSE_KEY = "close";
  private final String TASK_OPEN_KEY = "open";

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public Job save(Job job) {

    Optional<String> existingId = Optional.ofNullable(job.getId());

    if (existingId.isPresent()) {
      delete(job);
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

    try {
      scheduleOneTimeJob(OpenGatewayTask.class, TASK_OPEN_KEY, id, end, jobData);
      scheduleOneTimeJob(CloseGatewayTask.class, TASK_CLOSE_KEY, id, start, jobData);
    } catch (SchedulerException e) {
      throw new RuntimeException();
    }

    return job;
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
      builder.isPartial(taskCloseTrigger.isPresent() ? false : true);

      return Optional.of(builder.build());
    } catch (SchedulerException e) {
      throw new RuntimeException();
    }
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Page<Job> findAll(Pageable pageable) {
    try {
      List<String> jobIds = scheduler.getJobGroupNames();

      int jobCount = jobIds.size();
      int startIndex = (int) pageable.getOffset();

      if (startIndex >= jobCount) {
        return Page.empty(pageable);
      }

      int endIndex = Math.min(startIndex + pageable.getPageSize(), jobCount);

      List<Job> jobs = new ArrayList<>(endIndex - startIndex);

      for (int i = startIndex; i < endIndex; i++) {
        String jobId = jobIds.get(i);
        findById(jobId).ifPresent(jobs::add);
      }

      return new PageImpl<>(jobs, pageable, jobCount);

    } catch (SchedulerException e) {
      throw new RuntimeException();
    }
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void delete(Job job) {
    String id = job.getId();
    try {
      scheduler.deleteJob(new JobKey(TASK_CLOSE_KEY, id));
      scheduler.deleteJob(new JobKey(TASK_OPEN_KEY, id));
    } catch (SchedulerException e) {
      throw new RuntimeException();
    }
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
