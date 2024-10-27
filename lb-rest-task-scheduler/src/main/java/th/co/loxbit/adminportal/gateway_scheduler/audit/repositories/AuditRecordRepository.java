package th.co.loxbit.adminportal.gateway_scheduler.audit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import th.co.loxbit.adminportal.gateway_scheduler.audit.entities.AuditRecord;

@Repository
public interface AuditRecordRepository extends JpaRepository<AuditRecord, Long> {
}
