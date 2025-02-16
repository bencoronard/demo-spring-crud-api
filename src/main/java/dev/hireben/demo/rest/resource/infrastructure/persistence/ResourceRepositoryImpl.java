package dev.hireben.demo.rest.resource.infrastructure.persistence;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import dev.hireben.demo.rest.resource.domain.dto.Paginable;
import dev.hireben.demo.rest.resource.domain.dto.Paginated;
import dev.hireben.demo.rest.resource.domain.entity.Resource;
import dev.hireben.demo.rest.resource.domain.model.Tenant;
import dev.hireben.demo.rest.resource.domain.repository.ResourceRepository;
import dev.hireben.demo.rest.resource.infrastructure.persistence.jpa.JpaResourceRepository;
import dev.hireben.demo.rest.resource.infrastructure.persistence.jpa.entity.ResourceEntity;
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
  public void deleteByIdAndTenant(Long id, Tenant tenant) {
    repository.deleteByIdAndTenant(id, tenant);
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
  public Optional<Resource> findByIdAndTenant(Long id, Tenant tenant) {
    return repository.findByIdAndTenant(id, tenant).map(ResourceEntity::toDomain);
  }

}
