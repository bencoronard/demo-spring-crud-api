package th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.services.implementations;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  private final String DEFAULT_MESSAGE = "Hello, world";

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public void scheduleJob(Instant start, Instant end, String message, String initiator) {

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
        .initiator(initiator)
        .isPartial(false)
        .build();

    jobRepository.save(job);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void scheduleJobPartial(Instant end, String initiator) {

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
        .initiator(initiator)
        .isPartial(true)
        .build();

    jobRepository.save(job);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Job retrieveJobWithId(String id) {
    return jobRepository.findById(id).orElse(null);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Page<Job> retrieveAllJobs(Pageable pageable) {
    return jobRepository.findAll(pageable);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void rescheduleJob(String id, Instant start, Instant end, String message, String initiator) {

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
        .initiator(initiator)
        .isPartial(theJob.isPartial())
        .build();

    jobRepository.save(job);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void descheduleJobWithId(String id) {

    Optional<Job> existingJob = jobRepository.findById(id);

    if (!existingJob.isPresent()) {
      throw new RuntimeException();
    }

    Job theJob = existingJob.get();

    if (theJob.isPartial()) {
      throw new RuntimeException();
    }

    jobRepository.delete(existingJob.get());
  }

  // ---------------------------------------------------------------------------//
  // Utilities
  // ---------------------------------------------------------------------------//

}
