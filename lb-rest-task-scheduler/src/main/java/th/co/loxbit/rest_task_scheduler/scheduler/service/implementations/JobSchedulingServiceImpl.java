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
import th.co.loxbit.rest_task_scheduler.common.exceptions.WrappableException;
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
  public void scheduleJob(long start, long end, String message, String createdBy) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      checkValidStartEndTimes(start, end);

      String jobId = UUID.randomUUID().toString();

      Map<String, Object> jobData = new HashMap<>();
      jobData.put("message", message);
      jobData.put("owner", createdBy);

      Trigger closeGatewayTrigger = TriggerBuilder.newTrigger()
          .withIdentity("closeOneTime", jobId)
          .startAt(Date.from(Instant.ofEpochSecond(start)))
          .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
          .build();

      Trigger openGatewayTrigger = TriggerBuilder.newTrigger()
          .withIdentity("openOneTime", jobId)
          .startAt(Date.from(Instant.ofEpochSecond(end)))
          .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0))
          .build();

      scheduleRepository.createSchedule(jobId, jobData, closeGatewayTrigger, openGatewayTrigger);

      auditRecordService.addAuditRecord(jobId, start, end, createdBy, AuditRecordType.CREATE);

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void descheduleJob(String jobId, String deletedBy) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      GatewaySchedule deletedSchedule = scheduleRepository.deleteScheduleById(jobId);

      auditRecordService.addAuditRecord(jobId, deletedSchedule.getStartTime().getEpochSecond(),
          deletedSchedule.getEndTime().getEpochSecond(), deletedBy, AuditRecordType.DELETE);

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void updateJob(String jobId, long newStart, long newEnd, String newMessage, String updatedBy) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      checkValidStartEndTimes(newStart, newEnd);

      scheduleRepository.deleteScheduleById(jobId);

      scheduleJob(newStart, newEnd, newMessage, updatedBy);

      auditRecordService.addAuditRecord(jobId, newStart, newEnd, updatedBy, AuditRecordType.EDIT);

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  private void checkValidStartEndTimes(long start, long end) {

    String message1 = "End time must be after start time";
    String message2 = "Start time must be in the future";

    if (start > end) {
      throw new WrappableException(71, message1, message1);
    }

    if (start < Instant.now().getEpochSecond()) {
      throw new WrappableException(72, message2, message2);
    }

  }

}
