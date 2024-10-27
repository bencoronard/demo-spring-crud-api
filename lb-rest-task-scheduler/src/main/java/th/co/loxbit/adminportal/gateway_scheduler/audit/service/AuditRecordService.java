package th.co.loxbit.adminportal.gateway_scheduler.audit.service;

import java.time.Instant;

import th.co.loxbit.adminportal.gateway_scheduler.audit.entities.AuditRecordType;

public interface AuditRecordService {

  int SERVICE_CODE = 3000;

  void addRecord(String jobId, Instant startAt, Instant endAt, String createdBy, AuditRecordType userAction);

}
