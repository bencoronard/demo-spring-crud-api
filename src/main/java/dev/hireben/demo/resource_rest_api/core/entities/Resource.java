package dev.hireben.demo.resource_rest_api.core.entities;

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
public class Resource {

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

  @Column(name = "created_by", updatable = false, nullable = false)
  private String createdBy;

  @Enumerated(EnumType.STRING)
  @Column(name = "tenant", length = 12, updatable = false, nullable = false)
  private Tenant tenant;

  // ---------------------------------------------------------------------------//
  // Enums
  // ---------------------------------------------------------------------------//

  public enum Tenant {
    CONSENT, NOTIFICATION, INSURANCE, AUCTION
  }
}
