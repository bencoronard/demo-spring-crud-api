package dev.hireben.demo.resource_rest_api.anOldStruct.common.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonPropertyOrder({ "respCode", "respMsg", "data" })
public class GlobalResponseBody<T> {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String code;
  private final String message;
  private final T payload;

}
