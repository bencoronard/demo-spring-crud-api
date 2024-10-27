package th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import th.co.loxbit.adminportal.gateway_scheduler.job_scheduling.entities.Job;

public interface JobController {

  ResponseEntity<Void> createJob(Job job);

  ResponseEntity<List<Job>> ListJobs(Integer page);

  ResponseEntity<Job> fetchJobWithId(String id);

  ResponseEntity<Void> updateJobWithId(String id);

  ResponseEntity<Void> removeJobWithId(String id);

}
