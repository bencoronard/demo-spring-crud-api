package dev.hireben.api.rest.abstract_resource.common.dtos;

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

  private final String respCode;
  private final String respMsg;
  private final T data;

}
