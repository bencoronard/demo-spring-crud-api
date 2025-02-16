package dev.hireben.demo.rest.resource.application.usecase;

import dev.hireben.demo.rest.resource.application.dto.UserDTO;
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

  public Resource findResource(Long id, UserDTO user) {
    return repository.findByIdAndTenant(id, user.getTenant()).orElseThrow(() -> new RuntimeException());
  }

}
