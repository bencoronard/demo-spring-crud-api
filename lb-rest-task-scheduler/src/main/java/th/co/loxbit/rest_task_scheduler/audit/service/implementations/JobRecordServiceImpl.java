package th.co.loxbit.rest_task_scheduler.audit.service.implementations;

import java.time.Instant;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.audit.entities.JobRecord;
import th.co.loxbit.rest_task_scheduler.audit.entities.JobRecordType;
import th.co.loxbit.rest_task_scheduler.audit.repositories.JobRecordRepository;
import th.co.loxbit.rest_task_scheduler.audit.service.JobRecordService;
import th.co.loxbit.rest_task_scheduler.common.utilities.ServiceExceptionUtil;

@Service
@RequiredArgsConstructor
public class JobRecordServiceImpl implements JobRecordService {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final JobRecordRepository jobRecordRepository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public void addJobRecord(String jobId, long startAt, long endAt, String createdBy, long createAt,
      JobRecordType userAction) {
    ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      JobRecord jobRecord = JobRecord.builder()
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
