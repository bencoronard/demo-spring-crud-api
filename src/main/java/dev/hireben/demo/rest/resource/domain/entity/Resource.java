package dev.hireben.demo.rest.resource.domain.entity;

import java.time.Instant;

import dev.hireben.demo.rest.resource.domain.model.Tenant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resource {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private Long id;
  private String field1;
  private String field2;
  private String field3;
  private Tenant tenant;
  private String createdBy;
  private Instant createdAt;

}
