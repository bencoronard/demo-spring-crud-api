package dev.hireben.demo.rest.resource.domain.repository;

import java.util.Optional;

import dev.hireben.demo.rest.resource.domain.dto.Paginable;
import dev.hireben.demo.rest.resource.domain.dto.Paginated;
import dev.hireben.demo.rest.resource.domain.entity.Resource;
import dev.hireben.demo.rest.resource.domain.model.Tenant;

public interface ResourceRepository {

  Resource save(Resource resource);

  void delete(Resource resource);

  Paginated<Resource> findAllByTenant(Tenant tenant, Paginable paginable);

  Optional<Resource> findByIdAndTenant(Long id, Tenant tenant);

}
