package dev.hireben.demo.rest.resource.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateResourceDTO {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String field1;
  private final String field2;
  private final String field3;

}
