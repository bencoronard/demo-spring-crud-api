package th.co.loxbit.rest_task_scheduler.scheduler.service.implementations;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.audit.entities.JobRecordType;
import th.co.loxbit.rest_task_scheduler.audit.service.JobRecordService;
import th.co.loxbit.rest_task_scheduler.common.utilities.ServiceExceptionUtil;
import th.co.loxbit.rest_task_scheduler.scheduler.entities.GatewaySchedule;
import th.co.loxbit.rest_task_scheduler.scheduler.repository.ScheduleRepository;
import th.co.loxbit.rest_task_scheduler.scheduler.service.JobSchedulingService;

@Service
@RequiredArgsConstructor
public class JobSchedulingServiceImpl implements JobSchedulingService {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ScheduleRepository scheduleRepository;
  private final JobRecordService jobRecordService;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public List<GatewaySchedule> getScheduledJobs() {
    return ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      return scheduleRepository.findAllSchedules();

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void scheduleJob(int startTime, int endTime, String message, String owner) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      String jobId = UUID.randomUUID().toString();
      Instant startAt = Instant.ofEpochSecond(startTime);
      Instant endAt = Instant.ofEpochSecond(endTime);

      Map<String, Object> jobData = new HashMap<>();
      jobData.put("message", message);
      jobData.put("owner", owner);

      Trigger closeGatewayTrigger = TriggerBuilder.newTrigger()
          .withIdentity("closeOneTime", jobId)
          .startAt(Date.from(startAt))
          .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
          .build();

      Trigger openGatewayTrigger = TriggerBuilder.newTrigger()
          .withIdentity("openOneTime", jobId)
          .startAt(Date.from(endAt))
          .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
          .build();

      scheduleRepository.createSchedule(jobId, jobData, closeGatewayTrigger, openGatewayTrigger);

      jobRecordService.addJobRecord(jobId, startAt, endAt, owner, Instant.now(), JobRecordType.CREATE);

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void descheduleJob(String jobId) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      scheduleRepository.deleteScheduleById(jobId);

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void updateJob(String jobId, int newStartTime, int newEndTime, String newMessage, String newOwner) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      scheduleRepository.deleteScheduleById(jobId);

      scheduleJob(newStartTime, newEndTime, newMessage, newOwner);

      return null;

    }, SERVICE_CODE);
  }

}
