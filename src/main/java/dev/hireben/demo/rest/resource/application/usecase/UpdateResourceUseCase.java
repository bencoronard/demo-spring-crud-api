package dev.hireben.demo.rest.resource.application.usecase;

import dev.hireben.demo.rest.resource.application.dto.UpdateResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UserDTO;
import dev.hireben.demo.rest.resource.domain.entity.Resource;
import dev.hireben.demo.rest.resource.domain.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateResourceUseCase {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ResourceRepository repository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public void updateResource(Long id, UpdateResourceDTO dto, UserDTO user) {

    Resource resource = repository.findByIdAndTenant(id, user.getTenant()).orElseThrow(() -> new RuntimeException());

    resource.setField1(dto.getField1());
    resource.setField2(dto.getField2());
    resource.setField3(dto.getField3());

    repository.save(resource);
  }

}
