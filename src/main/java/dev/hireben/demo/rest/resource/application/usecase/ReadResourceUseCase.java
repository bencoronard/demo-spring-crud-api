package dev.hireben.demo.rest.resource.application.usecase;

import java.util.Collection;

import dev.hireben.demo.rest.resource.application.dto.ResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UserDTO;
import dev.hireben.demo.rest.resource.application.exception.ResourceNotFoundException;
import dev.hireben.demo.rest.resource.domain.dto.Paginable;
import dev.hireben.demo.rest.resource.domain.dto.Paginated;
import dev.hireben.demo.rest.resource.domain.entity.Resource;
import dev.hireben.demo.rest.resource.domain.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReadResourceUseCase {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ResourceRepository repository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public ResourceDTO findResource(Long id, UserDTO user) {

    Resource foundResource = repository.findByIdAndTenant(id, user.getTenant())
        .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

    return ResourceDTO.builder()
        .id(foundResource.getId())
        .field1(foundResource.getField1())
        .field2(foundResource.getField2())
        .field3(foundResource.getField3())
        .createdBy(foundResource.getCreatedBy())
        .build();
  }

  // ---------------------------------------------------------------------------//

  public Paginated<ResourceDTO> findAllResources(Paginable paginable, UserDTO user) {

    Paginated<Resource> page = repository.findAllByTenant(user.getTenant(), paginable);

    Collection<ResourceDTO> resourceDTOs = page.getContent().stream()
        .map(resource -> ResourceDTO.builder()
            .id(resource.getId())
            .field1(resource.getField1())
            .field2(resource.getField2())
            .field3(resource.getField3())
            .createdBy(resource.getCreatedBy())
            .build())
        .toList();

    return Paginated.<ResourceDTO>builder()
        .content(resourceDTOs)
        .pageNumber(page.getPageNumber())
        .pageSize(page.getPageSize())
        .totalElements(page.getTotalElements())
        .build();
  }

}
