package th.co.loxbit.adminportal.gateway_scheduler.scheduler.service.implementations;

import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.adminportal.gateway_scheduler.audit.entities.AuditRecordType;
import th.co.loxbit.adminportal.gateway_scheduler.audit.service.AuditRecordService;
import th.co.loxbit.adminportal.gateway_scheduler.common.utilities.KongRequestUtil;
import th.co.loxbit.adminportal.gateway_scheduler.common.utilities.ServiceExceptionUtil;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.services.GatewayService;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.dtos.GatewaySchedule;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.repository.ScheduleRepository;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.service.JobSchedulingService;

@Service
@RequiredArgsConstructor
public class JobSchedulingServiceImpl implements JobSchedulingService {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ScheduleRepository scheduleRepository;
  private final GatewayService gatewayService;
  private final AuditRecordService auditRecordService;

  private final KongRequestUtil kongRequestUtil;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public List<GatewaySchedule> getJobSchedules() {
    return ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      return scheduleRepository.findAllJobSchedules();

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public GatewaySchedule getJobScheduleWithId(String jobId) {
    return ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      return scheduleRepository.findJobScheduleById(jobId);

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void createJobSchedule(Instant start, Instant end, String message, String createdBy) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      String createdJobId = scheduleRepository.createJobSchedule(
          start,
          end,
          messageOrDefaultMsg(message),
          createdBy);

      auditRecordService.addRecord(createdJobId, start, end, createdBy, AuditRecordType.CREATE);

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void createPartialJobSchedule(Instant start, Instant end, String message, String createdBy) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      String createdJobId = scheduleRepository.createPartialJobSchedule(
          start,
          end,
          messageOrDefaultMsg(message),
          createdBy);

      auditRecordService.addRecord(createdJobId, start, end, createdBy, AuditRecordType.CREATE);

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void updateJobScheduleWithId(String jobId, Instant newStart, Instant newEnd, String newMessage,
      String updatedBy) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      boolean jobIsPartial = scheduleRepository.updateJobById(
          jobId,
          newStart,
          newEnd,
          messageOrDefaultMsg(newMessage),
          updatedBy);

      if (jobIsPartial) {
        gatewayService.closeGateway(newMessage, newStart, newEnd);
      }

      auditRecordService.addRecord(jobId, newStart, newEnd, updatedBy, AuditRecordType.EDIT);

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void deleteJobScheduleWithId(String jobId, String deletedBy) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      GatewaySchedule deleledJob = scheduleRepository.deleteJobScheduleById(jobId);

      auditRecordService.addRecord(jobId, deleledJob.getStart(), deleledJob.getEnd(), deletedBy,
          AuditRecordType.DELETE);

      return null;

    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//
  // Utilities
  // ---------------------------------------------------------------------------//

  private String messageOrDefaultMsg(String message) {
    return message != null && !message.trim().isEmpty() ? message : kongRequestUtil.getDefaultMessage();
  }

}
