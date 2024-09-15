package th.co.loxbit.rest_task_scheduler.audit.service;

import java.time.Instant;

import th.co.loxbit.rest_task_scheduler.audit.entities.JobRecordType;

public interface JobRecordService {

  int SERVICE_CODE = 3000;

  void addJobRecord(String jobId, Instant startAt, Instant endAt, String createdBy, Instant createAt,
      JobRecordType userAction);

}
