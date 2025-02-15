package dev.hireben.demo.resource_rest_api.anOldStruct.common.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FieldValidationExceptionMap {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String field;
  private final String message;

}
