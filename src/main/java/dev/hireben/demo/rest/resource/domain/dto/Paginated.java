package dev.hireben.demo.rest.resource.domain.dto;

import java.util.Collection;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Paginated<T> {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final Collection<T> content;
  private final int pageNumber;
  private final int pageSize;
  private final long totalElements;

}
