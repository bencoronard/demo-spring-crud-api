package dev.hireben.demo.rest.resource.domain.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Paginable {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final int pageNumber;
  private final int pageSize;
  private final Map<String, Boolean> sortFieldsDesc;

}
