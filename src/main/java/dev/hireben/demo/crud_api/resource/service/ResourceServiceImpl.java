package dev.hireben.demo.crud_api.resource.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.hireben.demo.common_libs.exception.InsufficientPermissionException;
import dev.hireben.demo.crud_api.resource.entity.Resource;
import dev.hireben.demo.crud_api.resource.exception.ResourceNotFoundException;
import dev.hireben.demo.crud_api.resource.repository.ResourceRepository;
import dev.hireben.demo.crud_api.resource.utility.ResourcePermission;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class ResourceServiceImpl implements ResourceService {

  private final ResourceRepository resourceRepository;

  private static final String RESOURCE_NOT_FOUND_FORMAT = "Resource: %d not found";

  // =============================================================================

  @Override
  @Transactional(readOnly = true)
  public Slice<Resource> listResources(Pageable pageable, Claims authClaims) {

    if (authClaims.get(ResourcePermission.LIST_RESOURCES, Integer.class) == null) {
      throw new InsufficientPermissionException("Not allowed to list resources");
    }

    return resourceRepository.findAllByCreatedBy(pageable, Long.parseLong(authClaims.getSubject()));
  }

  // -----------------------------------------------------------------------------

  @Override
  @Transactional(readOnly = true)
  public Resource retrieveResource(Long id, Claims authClaims) {

    if (authClaims.get(ResourcePermission.READ_RESOURCE, Integer.class) == null) {
      throw new InsufficientPermissionException("Not allowed to read resource");
    }

    return resourceRepository.findByIdAndCreatedBy(id, Long.parseLong(authClaims.getSubject()))
        .orElseThrow(() -> new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND_FORMAT, id)));
  }

  // -----------------------------------------------------------------------------

  @Override
  @Transactional
  public Long createResource(Resource dto, Claims authClaims) {

    if (authClaims.get(ResourcePermission.CREATE_RESOURCE, Integer.class) == null) {
      throw new InsufficientPermissionException("Not allowed to create resource");
    }

    dto.setId(null);
    dto.setVersion(null);
    dto.setCreatedBy(Long.parseLong(authClaims.getSubject()));

    return resourceRepository.save(dto).getId();
  }

  // -----------------------------------------------------------------------------

  @Override
  @Transactional
  public void updateResource(Long id, Resource dto, Claims authClaims) {

    if (authClaims.get(ResourcePermission.UPDATE_RESOURCE, Integer.class) == null) {
      throw new InsufficientPermissionException("Not allowed to update resource");
    }

    Resource entity = resourceRepository.findByIdAndCreatedBy(id, Long.parseLong(authClaims.getSubject()))
        .orElseThrow(() -> new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND_FORMAT, id)));

    entity.setVersion(dto.getVersion());
    entity.setTextField(dto.getTextField());
    entity.setNumberField(dto.getNumberField());
    entity.setBooleanField(dto.getBooleanField());

    resourceRepository.save(entity);
  }

  // -----------------------------------------------------------------------------

  @Override
  @Transactional
  public void deleteResource(Long id, Claims authClaims) {

    if (authClaims.get(ResourcePermission.DELETE_RESOURCE, Integer.class) == null) {
      throw new InsufficientPermissionException("Not allowed to delete resource");
    }

    Resource entity = resourceRepository.findByIdAndCreatedBy(id, Long.parseLong(authClaims.getSubject()))
        .orElseThrow(() -> new ResourceNotFoundException(String.format(RESOURCE_NOT_FOUND_FORMAT, id)));

    resourceRepository.delete(entity);
  }

}
