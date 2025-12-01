package dev.hireben.demo.crud_api.product.repository;

import java.util.Collection;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.hireben.demo.crud_api.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Product findByIdAndTenantId(Long id, Long tenantId);

  Slice<Product> findAllByTenantId(Pageable pageable, Long tenantId);

  void deleteByIdAndTenantId(Long id, Long tenantId);

  void deleteAllByIdAndTenantId(Collection<Long> id, Long tenantId);

}
