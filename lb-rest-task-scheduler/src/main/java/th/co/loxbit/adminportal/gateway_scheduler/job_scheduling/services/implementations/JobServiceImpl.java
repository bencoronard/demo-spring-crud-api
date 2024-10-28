package th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.entities.Job;
import th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.repositories.JobRepository;
import th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.services.JobService;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final JobRepository jobRepository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

}
