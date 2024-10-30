package th.co.loxbit.adminportal.gateway_scheduler.scheduler.services.implementations;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.adminportal.gateway_scheduler.common.utilities.ServiceExceptionUtil;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.entities.Job;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.repositories.JobRepository;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.services.JobService;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final JobRepository jobRepository;

  @Value("${api.external.gateway.properties.default-plugin-message}")
  private String DEFAULT_MESSAGE;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public Job scheduleJob(Instant start, Instant end, String message, String initiatedBy) {
    return ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      if (start == null || end == null) {
        throw new RuntimeException();
      }
      if (start.isBefore(Instant.now())) {
        throw new RuntimeException();
      }
      if (end.isBefore(start)) {
        throw new RuntimeException();
      }

      Job job = Job.builder()
          .start(start)
          .end(end)
          .message(message == null ? DEFAULT_MESSAGE : message)
          .initiator(initiatedBy)
          .isPartial(false)
          .build();

      return jobRepository.save(job);
    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Job scheduleJobPartial(Instant end, String initiatedBy) {
    return ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      if (end == null) {
        throw new RuntimeException();
      }
      if (end.isBefore(Instant.now())) {
        throw new RuntimeException();
      }

      Job job = Job.builder()
          .start(Instant.now())
          .end(end)
          .message(DEFAULT_MESSAGE)
          .initiator(initiatedBy)
          .isPartial(true)
          .build();

      return jobRepository.save(job);
    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Job retrieveJobWithId(String id) {
    return ServiceExceptionUtil.executeWithExceptionWrapper(() -> {
      return jobRepository.findById(id).orElse(null);
    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Page<Job> retrieveAllJobs(Pageable pageable) {
    return ServiceExceptionUtil.executeWithExceptionWrapper(() -> {
      return jobRepository.findAll(pageable);
    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Job rescheduleJob(String id, Instant start, Instant end, String message, String initiatedBy) {
    return ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      Optional<Job> existingJob = jobRepository.findById(id);

      if (!existingJob.isPresent()) {
        throw new RuntimeException();
      }

      Job theJob = existingJob.get();

      if (end == null) {
        throw new RuntimeException();
      }
      if (!theJob.isPartial()) {
        if (start == null) {
          throw new RuntimeException();
        }
        if (start.isBefore(Instant.now())) {
          throw new RuntimeException();
        }
        if (end.isBefore(start)) {
          throw new RuntimeException();
        }
      }
      if (end.isBefore(Instant.now())) {
        throw new RuntimeException();
      }

      Job job = Job.builder()
          .id(id)
          .start(start)
          .end(end)
          .message(message == null ? DEFAULT_MESSAGE : message)
          .initiator(initiatedBy)
          .isPartial(theJob.isPartial())
          .build();

      return jobRepository.save(job);
    }, SERVICE_CODE);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Job descheduleJobWithId(String id) {
    return ServiceExceptionUtil.executeWithExceptionWrapper(() -> {

      Optional<Job> existingJob = jobRepository.findById(id);

      if (!existingJob.isPresent()) {
        throw new RuntimeException();
      }

      Job job = existingJob.get();

      if (job.isPartial()) {
        throw new RuntimeException();
      }

      return jobRepository.delete(job);
    }, SERVICE_CODE);
  }

}
