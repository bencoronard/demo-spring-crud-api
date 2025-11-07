package dev.hireben.demo.crud_api.product;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
final class ProductServiceImpl implements ProductService {

  private final ProductRepository repository;

  // =============================================================================

  @Override
  public Long create(Claims authorization, ProductDTO product) {

    Product entity = Product.builder()
        .name("")
        .imageUrl("")
        .description("")
        .price(null)
        .cost(null)
        .tenantId(null)
        .category(Set.of())
        .build();

    if (entity == null) {
      throw new NullPointerException();
    }

    return repository.save(entity).getId();
  }

  // -----------------------------------------------------------------------------

  @Override
  public ProductDTO retrieveById(Claims authorization, Long productId) {

    if (productId == null) {
      throw new NullPointerException();
    }

    Product entity = repository.findById(productId).orElseThrow(NullPointerException::new);

    return ProductDTO.builder()
        .name("")
        .imageUrl("")
        .description("")
        .price(null)
        .cost(null)
        .category(entity.getCategory().stream().map(ProductCategory::getName).toList())
        .build();
  }

  // -----------------------------------------------------------------------------

  @Override
  public Slice<ProductDTO> retrieveAll(Claims authorization, Pageable pageable) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'retrieveAll'");
  }

  // -----------------------------------------------------------------------------

  @Override
  public void update(Claims authorization, Long productId, ProductDTO product) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  // -----------------------------------------------------------------------------

  @Override
  public void deleteById(Claims authorization, Long productId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
  }

  // -----------------------------------------------------------------------------

  @Override
  public void deleteAll(Claims authorization, Collection<Long> productIdList) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteAll'");
  }

}
