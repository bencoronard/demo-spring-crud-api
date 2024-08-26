package dev.hireben.demo.crud_api.common.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ResourceEntity {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Version
  @Column(nullable = false)
  private Long version;

  @Column(updatable = false, nullable = false)
  private Instant createdAt;

  @Column(nullable = false)
  private Instant lastUpdated;

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
