package dev.hireben.demo.rest.resource.presentation.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonPropertyOrder({ "code", "message", "data" })
public class GlobalResponseBody<T> {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String code;
  private final String message;
  private final T payload;

}
