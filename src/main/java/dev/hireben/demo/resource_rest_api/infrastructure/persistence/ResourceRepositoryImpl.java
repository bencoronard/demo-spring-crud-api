package dev.hireben.demo.resource_rest_api.infrastructure.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import dev.hireben.demo.resource_rest_api.domain.dto.Paginable;
import dev.hireben.demo.resource_rest_api.domain.dto.Paginated;
import dev.hireben.demo.resource_rest_api.domain.entity.Resource;
import dev.hireben.demo.resource_rest_api.domain.model.Tenant;
import dev.hireben.demo.resource_rest_api.domain.repository.ResourceRepository;
import dev.hireben.demo.resource_rest_api.infrastructure.persistence.jpa.JpaResourceRepository;
import dev.hireben.demo.resource_rest_api.infrastructure.persistence.jpa.entity.ResourceEntity;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ResourceRepositoryImpl implements ResourceRepository {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final JpaResourceRepository repository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public Resource save(Resource resource) {
    return repository.save(ResourceEntity.fromDomain(resource)).toDomain();
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void deleteByIdAndTenant(Long id, Tenant tenant) {
    repository.deleteByIdAndTenant(id, tenant);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Paginated<Resource> findAll(Paginable paginable) {
    Pageable pageable = PageRequest.of(paginable.getPageNumber(), paginable.getPageSize(),
        Sort.by(paginable.isDescending() ? Order.desc(paginable.getSortBy()) : Order.asc(paginable.getSortBy())));

    Page<ResourceEntity> page = repository.findAll(pageable);

    Collection<Resource> resources = page.getContent().stream().map(ResourceEntity::toDomain)
        .collect(Collectors.toList());

    return Paginated.<Resource>builder()
        .content(resources)
        .pageNumber(page.getNumber())
        .pageSize(page.getSize())
        .totalElements(page.getTotalElements())
        .build();
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Paginated<Resource> findAllByTenant(Tenant tenant, Paginable paginable) {

    Pageable pageable = PageRequest.of(paginable.getPageNumber(), paginable.getPageSize(),
        Sort.by(paginable.isDescending() ? Order.desc(paginable.getSortBy()) : Order.asc(paginable.getSortBy())));

    Page<ResourceEntity> page = repository.findAllByTenant(tenant, pageable);

    Collection<Resource> resources = page.getContent().stream().map(ResourceEntity::toDomain)
        .collect(Collectors.toList());

    return Paginated.<Resource>builder()
        .content(resources)
        .pageNumber(page.getNumber())
        .pageSize(page.getSize())
        .totalElements(page.getTotalElements())
        .build();
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Optional<Resource> findById(Long id) {
    return repository.findById(id).map(ResourceEntity::toDomain);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Optional<Resource> findByIdAndTenant(Long id, Tenant tenant) {
    return repository.findByIdAndTenant(id, tenant).map(ResourceEntity::toDomain);
  }

}
