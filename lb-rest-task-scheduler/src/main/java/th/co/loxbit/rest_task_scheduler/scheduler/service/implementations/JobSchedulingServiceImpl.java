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
import th.co.loxbit.rest_task_scheduler.audit.entities.AuditRecordType;
import th.co.loxbit.rest_task_scheduler.audit.service.AuditRecordService;
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
  private final AuditRecordService auditRecordService;

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
  public void scheduleJob(long startTime, long endTime, String message, String owner) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      String jobId = UUID.randomUUID().toString();

      Map<String, Object> jobData = new HashMap<>();
      jobData.put("message", message);
      jobData.put("owner", owner);

      Trigger closeGatewayTrigger = TriggerBuilder.newTrigger()
          .withIdentity("closeOneTime", jobId)
          .startAt(Date.from(Instant.ofEpochSecond(startTime)))
          .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
          .build();

      Trigger openGatewayTrigger = TriggerBuilder.newTrigger()
          .withIdentity("openOneTime", jobId)
          .startAt(Date.from(Instant.ofEpochSecond(endTime)))
          .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
          .build();

      scheduleRepository.createSchedule(jobId, jobData, closeGatewayTrigger, openGatewayTrigger);

      auditRecordService.addAuditRecord(jobId, startTime, endTime, owner, Instant.now().getEpochSecond(),
          AuditRecordType.CREATE);

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
  public void updateJob(String jobId, long newStartTime, long newEndTime, String newMessage, String newOwner) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      scheduleRepository.deleteScheduleById(jobId);

      scheduleJob(newStartTime, newEndTime, newMessage, newOwner);

      return null;

    }, SERVICE_CODE);
  }

}
