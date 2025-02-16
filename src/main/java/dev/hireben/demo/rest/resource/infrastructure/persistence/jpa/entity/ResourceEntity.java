package dev.hireben.demo.rest.resource.infrastructure.persistence.jpa.entity;

import java.time.Instant;

import dev.hireben.demo.rest.resource.domain.entity.Resource;
import dev.hireben.demo.rest.resource.domain.model.Tenant;
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

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resource", schema = "public")
public class ResourceEntity {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "field_1", nullable = false)
  private String field1;

  @Column(name = "field_2", nullable = false)
  private String field2;

  @Column(name = "field_3", nullable = false)
  private String field3;

  @Enumerated(EnumType.STRING)
  @Column(name = "tenant", length = 12, updatable = false, nullable = false)
  private Tenant tenant;

  @Column(name = "created_by", updatable = false, nullable = false)
  private String createdBy;

  @Column(name = "created_at", updatable = false, nullable = false)
  private Instant createdAt;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public static ResourceEntity fromDomain(Resource resource) {
    return ResourceEntity.builder()
        .id(resource.getId())
        .field1(resource.getField1())
        .field2(resource.getField2())
        .field3(resource.getField3())
        .tenant(resource.getTenant())
        .createdBy(resource.getCreatedBy())
        .createdAt(resource.getCreatedAt())
        .build();
  }

  public Resource toDomain() {
    return Resource.builder()
        .id(this.id)
        .field1(this.field1)
        .field2(this.field2)
        .field3(this.field3)
        .tenant(this.tenant)
        .createdBy(this.createdBy)
        .createdAt(this.createdAt)
        .build();
  }

}
