package dev.hireben.demo.resource_rest_api.application.service;

import dev.hireben.demo.resource_rest_api.domain.entity.Resource;
import dev.hireben.demo.resource_rest_api.domain.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReadResourceUseCase {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ResourceRepository resourceRepository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public Resource findResource(Long id) {
    return resourceRepository.findById(id).orElseThrow(null);
  }

}
