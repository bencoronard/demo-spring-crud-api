package dev.hireben.demo.resource_rest_api.domain.entity;

import dev.hireben.demo.resource_rest_api.domain.model.Tenant;
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
  private String createdBy;
  private Tenant tenant;

}
