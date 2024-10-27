package th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.entities.Job;

public interface JobRepository {

  Job save(Job job);

  Optional<Job> findById(String id);

  Page<Job> findAll(Pageable pageable);

  void delete(Job job);

}
