package dev.hireben.demo.crud_api.resource.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "resources", schema = "public")
public class Resource {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Version
  @Column(nullable = false)
  private Long version;

  @Column(updatable = false, nullable = false)
  private Long createdBy;

  @Column(updatable = false, nullable = false)
  private Instant createdAt;

  @Column(nullable = false)
  private Instant lastUpdated;

  private String textField;

  private Integer numberField;

  private Boolean booleanField;

  // =============================================================================

  @PrePersist
  protected final void onCreate() {
    Instant now = Instant.now();
    createdAt = now;
    lastUpdated = now;
  }

  // -----------------------------------------------------------------------------

  @PreUpdate
  protected final void onUpdate() {
    lastUpdated = Instant.now();
  }

}
