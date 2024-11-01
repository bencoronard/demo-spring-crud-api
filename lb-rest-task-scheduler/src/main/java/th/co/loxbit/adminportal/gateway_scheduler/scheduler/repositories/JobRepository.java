package th.co.loxbit.adminportal.gateway_scheduler.scheduler.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import th.co.loxbit.adminportal.gateway_scheduler.scheduler.entities.Job;

public interface JobRepository {

  Job save(Job job);

  Optional<Job> findById(String id, boolean compact);

  Page<Job> findAll(Pageable pageable);

  Job delete(Job job);

}
