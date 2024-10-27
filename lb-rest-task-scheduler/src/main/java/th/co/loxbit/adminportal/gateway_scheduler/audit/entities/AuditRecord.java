package th.co.loxbit.adminportal.gateway_scheduler.audit.entities;

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
@Table(name = "t_gateway_activity", schema = "admin_portal")
public class AuditRecord {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "activity_id", nullable = false)
  private Long id;

  @Column(name = "job_id", length = 36, updatable = false, nullable = false)
  private String jobId;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_action", length = 8, updatable = false, nullable = false)
  private AuditRecordType action;

  @Column(name = "job_start_at", updatable = false, nullable = false)
  private Instant startAt;

  @Column(name = "job_end_at", updatable = false, nullable = false)
  private Instant endAt;

  @Column(name = "created_by", length = 36, updatable = false, nullable = false)
  private String createdBy;

  @Column(name = "created_at", updatable = false, nullable = false)
  private Instant createdAt;

}
