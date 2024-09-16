package th.co.loxbit.rest_task_scheduler.audit.service;

import th.co.loxbit.rest_task_scheduler.audit.entities.JobRecordType;

public interface JobRecordService {

  int SERVICE_CODE = 3000;

  void addJobRecord(String jobId, long startAt, long endAt, String createdBy, long createAt,
      JobRecordType userAction);

}
