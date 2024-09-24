package th.co.loxbit.rest_task_scheduler.scheduler.repository.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.scheduler.entities.GatewaySchedule;
import th.co.loxbit.rest_task_scheduler.scheduler.entities.tasks.CloseGatewayTask;
import th.co.loxbit.rest_task_scheduler.scheduler.entities.tasks.OpenGatewayTask;
import th.co.loxbit.rest_task_scheduler.scheduler.repository.ScheduleRepository;

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
  public Map<String, Pair<JobDetail, ? extends Trigger>> findScheduleById(String jobId) throws SchedulerException {

    Map<String, Pair<JobDetail, ? extends Trigger>> schedules = new HashMap<>();

    JobKey closeGatewayTaskKey = new JobKey(CLOSE_TASK_KEY, jobId);
    JobDetail closeGatewayTaskDetail = scheduler.getJobDetail(closeGatewayTaskKey);

    if (closeGatewayTaskDetail == null) {
      throw new SchedulerException("CloseTask not found for jobId: " + jobId);
    }

    List<? extends Trigger> closeGatewayTaskTriggers = scheduler.getTriggersOfJob(closeGatewayTaskKey);
    schedules.put(CLOSE_TASK_KEY, Pair.of(closeGatewayTaskDetail, closeGatewayTaskTriggers.get(0)));

    JobKey openGatewayTaskKey = new JobKey(OPEN_TASK_KEY, jobId);
    JobDetail openGatewayTaskDetail = scheduler.getJobDetail(openGatewayTaskKey);

    if (openGatewayTaskDetail == null) {
      throw new SchedulerException("OpenTask not found for jobId: " + jobId);
    }

    List<? extends Trigger> openGatewayTaskTriggers = scheduler.getTriggersOfJob(openGatewayTaskKey);
    schedules.put(OPEN_TASK_KEY, Pair.of(openGatewayTaskDetail, openGatewayTaskTriggers.get(0)));

    return schedules;
  }

  // ---------------------------------------------------------------------------//

  @Override
  public List<GatewaySchedule> findAllSchedules() throws SchedulerException {

    List<GatewaySchedule> gatewaySchedules = new ArrayList<>();

    for (String jobId : scheduler.getJobGroupNames()) {
      Map<String, Pair<JobDetail, ? extends Trigger>> schedules = findScheduleById(jobId);
      Pair<JobDetail, ? extends Trigger> closeGatewaySchedule = schedules.get(CLOSE_TASK_KEY);
      Pair<JobDetail, ? extends Trigger> openGatewaySchedule = schedules.get(OPEN_TASK_KEY);

      gatewaySchedules.add(GatewaySchedule.builder()
          .id(jobId)
          .startTime(closeGatewaySchedule.getSecond().getStartTime().toInstant())
          .endTime(openGatewaySchedule.getSecond().getStartTime().toInstant())
          .build());

    }

    return gatewaySchedules;
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void createSchedule(String jobId, Map<String, Object> jobData, Trigger jobTriggerStart, Trigger jobTriggerEnd)
      throws SchedulerException {

    JobDetail closeGatewayTask = JobBuilder.newJob(CloseGatewayTask.class).withIdentity(CLOSE_TASK_KEY, jobId)
        .usingJobData(new JobDataMap(jobData))
        .build();

    JobDetail openGatewayTask = JobBuilder.newJob(OpenGatewayTask.class).withIdentity(OPEN_TASK_KEY, jobId).build();

    scheduler.scheduleJob(closeGatewayTask, jobTriggerStart);
    scheduler.scheduleJob(openGatewayTask, jobTriggerEnd);

  }

  // ---------------------------------------------------------------------------//

  @Override
  public GatewaySchedule deleteScheduleById(String jobId) throws SchedulerException {

    Map<String, Pair<JobDetail, ? extends Trigger>> schedules = findScheduleById(jobId);
    Pair<JobDetail, ? extends Trigger> closeGatewaySchedule = schedules.get(CLOSE_TASK_KEY);
    Pair<JobDetail, ? extends Trigger> openGatewaySchedule = schedules.get(OPEN_TASK_KEY);

    JobKey closeGatewayTaskKey = new JobKey(CLOSE_TASK_KEY, jobId);
    scheduler.deleteJob(closeGatewayTaskKey);

    JobKey openGatewayTaskKey = new JobKey(OPEN_TASK_KEY, jobId);
    scheduler.deleteJob(openGatewayTaskKey);

    return GatewaySchedule.builder()
        .id(jobId)
        .startTime(closeGatewaySchedule.getSecond().getStartTime().toInstant())
        .endTime(openGatewaySchedule.getSecond().getStartTime().toInstant())
        .build();

  }

}
