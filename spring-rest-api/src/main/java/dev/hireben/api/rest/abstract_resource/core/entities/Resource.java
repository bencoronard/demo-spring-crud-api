package dev.hireben.api.rest.abstract_resource.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String field1;

  @Column(nullable = false)
  private String field2;

  @Column(nullable = false)
  private String field3;

  @Column(nullable = false)
  private String tenant;

  @Column(nullable = false)
  private String createdBy;
}
