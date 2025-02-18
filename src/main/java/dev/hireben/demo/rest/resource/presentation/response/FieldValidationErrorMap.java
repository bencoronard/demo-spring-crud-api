package dev.hireben.demo.rest.resource.presentation.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonPropertyOrder({ "field", "message" })
public class FieldValidationErrorMap {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String field;
  private final String message;

}
