package th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.repositories;

import java.util.List;

import th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.entities.Job;

public interface JobRepository {

  void createJob(Job job);

  Job findJobById(String id);

  List<Job> findAllJobs();

  void updateJob(Job job);

  void deleteJob(String id);

}
