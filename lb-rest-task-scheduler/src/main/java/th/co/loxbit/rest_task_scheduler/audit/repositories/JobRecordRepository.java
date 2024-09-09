package th.co.loxbit.rest_task_scheduler.audit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import th.co.loxbit.rest_task_scheduler.audit.entities.JobRecord;

@Repository
public interface JobRecordRepository extends JpaRepository<JobRecord, Long> {
}
