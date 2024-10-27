package th.co.loxbit.adminportal.gateway_scheduler.audit.service.implementations;

import java.time.Instant;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.adminportal.gateway_scheduler.audit.entities.AuditRecord;
import th.co.loxbit.adminportal.gateway_scheduler.audit.entities.AuditRecordType;
import th.co.loxbit.adminportal.gateway_scheduler.audit.repositories.AuditRecordRepository;
import th.co.loxbit.adminportal.gateway_scheduler.audit.service.AuditRecordService;
import th.co.loxbit.adminportal.gateway_scheduler.common.utilities.ServiceExceptionUtil;

@Service
@RequiredArgsConstructor
public class AuditRecordServiceImpl implements AuditRecordService {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final AuditRecordRepository auditRecordRepository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public void addRecord(String jobId, Instant startAt, Instant endAt, String createdBy, AuditRecordType userAction) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      AuditRecord auditRecord = AuditRecord.builder()
          .jobId(jobId)
          .startAt(startAt)
          .endAt(endAt)
          .createdBy(createdBy)
          .createdAt(Instant.now())
          .action(userAction)
          .build();

      auditRecordRepository.save(auditRecord);

      return null;

    }, SERVICE_CODE);
  }

}
