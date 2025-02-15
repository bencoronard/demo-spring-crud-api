package dev.hireben.demo.resource_rest_api.domain.dto;

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
  private final String sortBy;
  private final String sortOrder;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public boolean isDescending() {
    return "desc".equalsIgnoreCase(sortOrder);
  }

}
