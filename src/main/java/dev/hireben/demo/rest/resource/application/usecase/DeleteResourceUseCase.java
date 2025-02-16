package dev.hireben.demo.rest.resource.application.usecase;

import dev.hireben.demo.rest.resource.application.dto.UserDTO;
import dev.hireben.demo.rest.resource.domain.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteResourceUseCase {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ResourceRepository repository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public void deleteResource(Long id, UserDTO user) {
    repository.deleteByIdAndTenant(id, user.getTenant());
  }

}
