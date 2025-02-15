package dev.hireben.demo.resource_rest_api.infrastructure.persistence.jpa;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.hireben.demo.resource_rest_api.domain.model.Tenant;
import dev.hireben.demo.resource_rest_api.infrastructure.persistence.jpa.entity.ResourceEntity;

public interface JpaResourceRepository extends JpaRepository<ResourceEntity, Long> {

  void deleteByIdAndTenant(Long id, Tenant tenant);

  Page<ResourceEntity> findAllByTenant(Tenant tenant, Pageable pageable);

  Optional<ResourceEntity> findByIdAndTenant(Long id, Tenant tenant);

}
