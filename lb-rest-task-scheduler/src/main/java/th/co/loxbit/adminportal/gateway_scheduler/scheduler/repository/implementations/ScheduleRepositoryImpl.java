package th.co.loxbit.adminportal.gateway_scheduler.scheduler.repository.implementations;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Date;
import java.util.ArrayList;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.dtos.GatewaySchedule;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.dtos.GatewaySchedule.GatewayScheduleBuilder;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.entities.GatewayJob;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.entities.tasks.CloseGatewayTask;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.entities.tasks.OpenGatewayTask;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.exceptions.IllegalEndTimeException;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.exceptions.IllegalStartTimeException;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.exceptions.JobNotFoundException;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.repository.ScheduleRepository;

@Component
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final Scheduler scheduler;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public String createJobSchedule(Instant start, Instant end, String message, String createdBy)
      throws SchedulerException {

    validateStartEndTimes(start, end);

    String jobId = UUID.randomUUID().toString();
    Map<String, Object> jobData = new HashMap<>();

    jobData.put(JDATA_CREATOR_KEY, createdBy);
    jobData.put(JDATA_MSG_KEY, message);
    jobData.put(JDATA_START_KEY, start);

    scheduleOneTimeJob(OpenGatewayTask.class, OPEN_TASK_KEY, jobId, end, jobData);

    jobData.put(JDATA_END_KEY, end);

    scheduleOneTimeJob(CloseGatewayTask.class, CLOSE_TASK_KEY, jobId, start, jobData);

    return jobId;
  }

  // ---------------------------------------------------------------------------//

  @Override
  public String createPartialJobSchedule(Instant start, Instant end, String message, String createdBy)
      throws SchedulerException {

    validateStartEndTimes(null, end);

    String jobId = UUID.randomUUID().toString();
    Map<String, Object> jobData = new HashMap<>();

    jobData.put(JDATA_CREATOR_KEY, createdBy);
    jobData.put(JDATA_MSG_KEY, message);
    jobData.put(JDATA_START_KEY, start);
    jobData.put(JDATA_END_KEY, end);

    scheduleOneTimeJob(OpenGatewayTask.class, OPEN_TASK_KEY, jobId, end, jobData);

    return jobId;
  }

  // ---------------------------------------------------------------------------//

  @Override
  public GatewaySchedule findJobScheduleById(String jobId) throws SchedulerException {

    GatewayJob gatewayJob = findJobById(jobId);

    return parseGatewayJob(gatewayJob, true);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public List<GatewaySchedule> findAllJobSchedules() throws SchedulerException {

    List<GatewaySchedule> scheduleList = new ArrayList<>();

    for (String jobId : scheduler.getJobGroupNames()) {
      GatewayJob gatewayJob = findJobById(jobId);
      scheduleList.add(parseGatewayJob(gatewayJob, false));
    }

    return scheduleList;
  }

  // ---------------------------------------------------------------------------//

  @Override
  public GatewaySchedule deleteJobScheduleById(String jobId) throws SchedulerException {

    GatewayJob scheduleToDelete = findJobById(jobId);

    JobDetail closeTaskDetail = scheduleToDelete.getCloseTaskDetail();
    if (closeTaskDetail != null) {
      scheduler.deleteJob(closeTaskDetail.getKey());
    }
    JobDetail openTaskDetail = scheduleToDelete.getOpenTaskDetail();
    if (openTaskDetail != null) {
      scheduler.deleteJob(openTaskDetail.getKey());
    }

    return parseGatewayJob(scheduleToDelete, false);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public boolean updateJobById(String jobId, Instant newStart, Instant newEnd, String newMessage, String updatedBy)
      throws SchedulerException {

    GatewayJob existingJob = findJobById(jobId);

    boolean jobIsPartial = false;

    Trigger closeTaskTrigger = existingJob.getCloseTaskTrigger();
    if (closeTaskTrigger != null) {

      validateStartEndTimes(newStart, newEnd);
      validateStartEndTimes(null, newEnd);

      JobDetail closeTaskDetail = existingJob.getCloseTaskDetail();

      scheduler.deleteJob(closeTaskDetail.getKey());

      JobDataMap closeTaskData = closeTaskDetail.getJobDataMap();

      closeTaskData.put(JDATA_CREATOR_KEY, updatedBy);
      closeTaskData.put(JDATA_MSG_KEY, newMessage);
      closeTaskData.put(JDATA_START_KEY, newStart);
      closeTaskData.put(JDATA_END_KEY, newEnd);

      scheduleOneTimeJob(CloseGatewayTask.class, CLOSE_TASK_KEY, jobId, newStart, closeTaskData);
    } else {
      jobIsPartial = true;
    }

    Trigger openTaskTrigger = existingJob.getOpenTaskTrigger();
    if (openTaskTrigger != null) {

      validateStartEndTimes(null, newEnd);

      JobDetail openTaskDetail = existingJob.getOpenTaskDetail();

      scheduler.deleteJob(openTaskDetail.getKey());

      JobDataMap openTaskData = openTaskDetail.getJobDataMap();

      openTaskData.put(JDATA_CREATOR_KEY, updatedBy);
      openTaskData.put(JDATA_MSG_KEY, newMessage);
      openTaskData.put(JDATA_START_KEY, newStart);

      scheduleOneTimeJob(OpenGatewayTask.class, OPEN_TASK_KEY, jobId, newEnd, openTaskData);
    }

    return jobIsPartial;
  }

  // ---------------------------------------------------------------------------//
  // Utilities
  // ---------------------------------------------------------------------------//

  private void validateStartEndTimes(Instant start, Instant end) {

    Instant now = Instant.now();

    if (end.isBefore(now)) {
      throw new IllegalEndTimeException("Received illegal end time");
    }

    if (start != null && (start.isAfter(end) || start.isBefore(now))) {
      throw new IllegalStartTimeException("Received illegal start time");
    }

  }

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

  // ---------------------------------------------------------------------------//

  private GatewayJob findJobById(String jobId) throws SchedulerException {

    Trigger closeTaskTrigger = scheduler.getTrigger(new TriggerKey(CLOSE_TASK_KEY, jobId));
    Trigger openTaskTrigger = scheduler.getTrigger(new TriggerKey(OPEN_TASK_KEY, jobId));

    if (closeTaskTrigger == null && openTaskTrigger == null) {
      throw new JobNotFoundException("Job: " + jobId + " not found");
    }

    GatewayJob.GatewayJobBuilder builder = GatewayJob.builder();

    builder.id(jobId);

    if (closeTaskTrigger != null) {
      JobDetail closeTaskDetail = scheduler.getJobDetail(closeTaskTrigger.getJobKey());
      builder.closeTaskDetail(closeTaskDetail);
      builder.closeTaskTrigger(closeTaskTrigger);
    }

    if (openTaskTrigger != null) {
      JobDetail openTaskDetail = scheduler.getJobDetail(openTaskTrigger.getJobKey());
      builder.openTaskDetail(openTaskDetail);
      builder.openTaskTrigger(openTaskTrigger);
    }

    return builder.build();
  }

  // ---------------------------------------------------------------------------//

  private GatewaySchedule parseGatewayJob(GatewayJob gatewayJob, boolean includeMessage) {

    Trigger closeTaskTrigger = gatewayJob.getCloseTaskTrigger();
    Trigger openTaskTrigger = gatewayJob.getOpenTaskTrigger();

    GatewayScheduleBuilder builder = GatewaySchedule.builder();

    builder.id(gatewayJob.getId());

    if (closeTaskTrigger != null) {
      JobDataMap jobDataClose = gatewayJob.getCloseTaskDetail().getJobDataMap();
      builder.start(closeTaskTrigger.getStartTime().toInstant());
      builder.creator(jobDataClose.get(JDATA_CREATOR_KEY).toString());
      builder.end((Instant) jobDataClose.get(JDATA_END_KEY));
      if (includeMessage) {
        builder.message(jobDataClose.get(JDATA_MSG_KEY).toString());
      }
    } else {
      builder.partial(true);
    }

    if (openTaskTrigger != null) {
      JobDataMap jobDataOpen = gatewayJob.getOpenTaskDetail().getJobDataMap();
      builder.end(openTaskTrigger.getStartTime().toInstant());
      builder.creator(jobDataOpen.get(JDATA_CREATOR_KEY).toString());
      builder.start((Instant) jobDataOpen.get(JDATA_START_KEY));
      if (includeMessage) {
        builder.message(jobDataOpen.get(JDATA_MSG_KEY).toString());
      }
    }

    return builder.build();
  }

}
