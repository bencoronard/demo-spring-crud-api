package dev.hireben.demo.resource_rest_api.application.service;

import dev.hireben.demo.resource_rest_api.domain.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteResourceUseCase {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ResourceRepository resourceRepository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public void deleteResource(Long id) {
    resourceRepository.deleteById(id);
  }

}
