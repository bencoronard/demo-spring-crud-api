package dev.hireben.demo.resource_rest_api.core.repositories;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.hireben.demo.resource_rest_api.core.entities.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

  Optional<Resource> findByIdAndTenant(Long id, Resource.Tenant tenant);

  Collection<Resource> findAllByTenant(Resource.Tenant tenant);

}
