package th.co.loxbit.rest_task_scheduler.audit.service.implementations;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.audit.repositories.JobRecordRepository;
import th.co.loxbit.rest_task_scheduler.audit.service.JobRecordService;

@Service
@RequiredArgsConstructor
public class JobRecordServiceImpl implements JobRecordService {

  private JobRecordRepository jobRecordRepository;

  @Override
  public void addAuditRecord() {
    jobRecordRepository.count();
  }

}
