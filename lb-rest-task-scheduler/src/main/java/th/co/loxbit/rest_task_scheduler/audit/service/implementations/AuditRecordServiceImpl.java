package th.co.loxbit.rest_task_scheduler.audit.service.implementations;

import java.time.Instant;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.audit.entities.AuditRecord;
import th.co.loxbit.rest_task_scheduler.audit.entities.AuditRecordType;
import th.co.loxbit.rest_task_scheduler.audit.repositories.AuditRecordRepository;
import th.co.loxbit.rest_task_scheduler.audit.service.AuditRecordService;
import th.co.loxbit.rest_task_scheduler.common.utilities.ServiceExceptionUtil;

@Service
@RequiredArgsConstructor
public class AuditRecordServiceImpl implements AuditRecordService {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final AuditRecordRepository jobRecordRepository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public void addAuditRecord(String jobId, long startAt, long endAt, String createdBy, long createAt,
      AuditRecordType userAction) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      AuditRecord jobRecord = AuditRecord.builder()
          .jobId(jobId)
          .startAt(Instant.ofEpochSecond(startAt))
          .endAt(Instant.ofEpochSecond(endAt))
          .createdBy(createdBy)
          .createdAt(Instant.ofEpochSecond(createAt))
          .action(userAction)
          .build();

      jobRecordRepository.save(jobRecord);

      return null;

    }, SERVICE_CODE);
  }

}
