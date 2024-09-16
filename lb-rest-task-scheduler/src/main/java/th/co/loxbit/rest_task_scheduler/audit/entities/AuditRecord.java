package th.co.loxbit.rest_task_scheduler.audit.entities;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gateway_schedule_history", schema = "admin_portal")
public class AuditRecord {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "job_id", updatable = false, nullable = false)
  private String jobId;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_action", updatable = false, nullable = false)
  private AuditRecordType action;

  @Column(name = "job_start_at", updatable = false, nullable = false)
  private Instant startAt;

  @Column(name = "job_end_at", updatable = false, nullable = false)
  private Instant endAt;

  @Column(name = "created_by", updatable = false, nullable = false)
  private String createdBy;

  @Column(name = "created_at", updatable = false, nullable = false)
  private Instant createdAt;

}
