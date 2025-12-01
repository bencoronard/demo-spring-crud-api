package dev.hireben.demo.crud_api.product.service;

import java.util.Collection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import dev.hireben.demo.crud_api.product.dto.ProductDTO;
import io.jsonwebtoken.Claims;

public interface ProductService {

  Long create(Claims authorization, ProductDTO product);

  ProductDTO retrieveById(Claims authorization, Long productId);

  Slice<ProductDTO> retrieveAll(Claims authorization, Pageable pageable);

  Long update(Claims authorization, Long productId, ProductDTO product);

  void deleteById(Claims authorization, Long productId);

  void deleteAll(Claims authorization, Collection<Long> productIdList);

}
