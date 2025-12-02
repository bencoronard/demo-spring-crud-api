package dev.hireben.demo.crud_api.resource.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.hireben.demo.crud_api.resource.entity.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

  Slice<Resource> findAllByCreatedBy(Pageable pageable, Long createdBy);

  Optional<Resource> findByIdAndCreatedBy(Long id, Long createdBy);

}
