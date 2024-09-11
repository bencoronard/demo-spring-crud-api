package th.co.loxbit.rest_task_scheduler.audit.service.implementations;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.audit.entities.JobRecord;
import th.co.loxbit.rest_task_scheduler.audit.entities.JobRecordType;
import th.co.loxbit.rest_task_scheduler.audit.repositories.JobRecordRepository;
import th.co.loxbit.rest_task_scheduler.audit.service.JobRecordService;

@Service
@RequiredArgsConstructor
public class JobRecordServiceImpl implements JobRecordService {

  @Autowired
  private JobRecordRepository jobRecordRepository;

  @Override
  public void addJobRecord(String jobId, Instant startAt, Instant endAt, String createdBy, JobRecordType userAction) {

    JobRecord jobRecord = JobRecord.builder()
        .jobId(jobId)
        .startAt(startAt)
        .endAt(endAt)
        .createdBy(createdBy)
        .action(userAction)
        .build();

    jobRecordRepository.save(jobRecord);
  }

}
