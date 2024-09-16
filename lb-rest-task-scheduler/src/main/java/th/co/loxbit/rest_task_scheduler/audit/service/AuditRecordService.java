package th.co.loxbit.rest_task_scheduler.audit.service;

import th.co.loxbit.rest_task_scheduler.audit.entities.AuditRecordType;

public interface AuditRecordService {

  int SERVICE_CODE = 3000;

  void addAuditRecord(String jobId, long startAt, long endAt, String createdBy, long createAt,
      AuditRecordType userAction);

}
