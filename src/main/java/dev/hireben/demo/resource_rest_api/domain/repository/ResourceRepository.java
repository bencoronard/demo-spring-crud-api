package dev.hireben.demo.resource_rest_api.domain.repository;

import java.util.Optional;

import dev.hireben.demo.resource_rest_api.domain.dto.Paginable;
import dev.hireben.demo.resource_rest_api.domain.dto.Paginated;
import dev.hireben.demo.resource_rest_api.domain.entity.Resource;
import dev.hireben.demo.resource_rest_api.domain.model.Tenant;

public interface ResourceRepository {

  Resource save(Resource resource);

  void deleteById(Long id);

  void deleteByIdAndTenant(Long id, Tenant tenant);

  Paginated<Resource> findAll(Paginable paginable);

  Paginated<Resource> findAllByTenant(Tenant tenant, Paginable paginable);

  Optional<Resource> findById(Long id);

  Optional<Resource> findByIdAndTenant(Long id, Tenant tenant);

}
