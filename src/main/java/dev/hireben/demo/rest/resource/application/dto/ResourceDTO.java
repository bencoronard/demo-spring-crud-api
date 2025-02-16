package dev.hireben.demo.rest.resource.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResourceDTO {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final Long id;
  private final String field1;
  private final String field2;
  private final String field3;
  private final String createdBy;

}
