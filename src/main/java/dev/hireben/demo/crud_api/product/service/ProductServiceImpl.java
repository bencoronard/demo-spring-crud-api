package dev.hireben.demo.crud_api.product.service;

import java.util.Collection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import dev.hireben.demo.common_libs.exception.InsufficientPermissionException;
import dev.hireben.demo.crud_api.product.dto.ProductDTO;
import dev.hireben.demo.crud_api.product.entity.Product;
import dev.hireben.demo.crud_api.product.entity.ProductCategory;
import dev.hireben.demo.crud_api.product.exception.ProductNotFoundException;
import dev.hireben.demo.crud_api.product.repository.ProductCategoryRepository;
import dev.hireben.demo.crud_api.product.repository.ProductRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
final class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductCategoryRepository productCategoryRepository;

  // =============================================================================

  @Override
  public Long create(Claims authorization, ProductDTO product) {

    String permissionToken = authorization.get("CREATE_PRODUCT", String.class);
    if (permissionToken == null) {
      throw new InsufficientPermissionException("Not allowed to create new product");
    }

    Collection<ProductCategory> categories = product.getCategories().stream()
        .map(id -> productCategoryRepository.getReferenceById(id)).toList();

    Product entity = Product.builder()
        .name(product.getName())
        .imageUrl(product.getImageUrl())
        .description(product.getDescription())
        .price(product.getPrice())
        .cost(product.getCost())
        .tenantId(Long.parseLong(authorization.getSubject()))
        .categories(categories)
        .build();

    return productRepository.save(entity).getId();
  }

  // -----------------------------------------------------------------------------

  @Override
  public ProductDTO retrieveById(Claims authorization, Long productId) {

    String permissionToken = authorization.get("RETRIEVE_PRODUCT", String.class);
    if (permissionToken == null) {
      throw new InsufficientPermissionException("Not allowed to retrieve product");
    }

    Product entity = productRepository.findByIdAndTenantId(productId, Long.parseLong(authorization.getSubject()));
    if (entity == null) {
      throw new ProductNotFoundException(String.format("Product %s not found", productId));
    }

    return ProductDTO.builder()
        .name(entity.getName())
        .imageUrl(entity.getImageUrl())
        .description(entity.getDescription())
        .price(entity.getPrice())
        .cost(entity.getCost())
        .categories(entity.getCategories().stream().map(ProductCategory::getId).toList())
        .build();
  }

  // -----------------------------------------------------------------------------

  @Override
  public Slice<ProductDTO> retrieveAll(Claims authorization, Pageable pageable) {

    Slice<Product> entitySlice = productRepository.findAllByTenantId(pageable,
        Long.parseLong(authorization.getSubject()));

    Collection<ProductDTO> dto = entitySlice.getContent().stream()
        .map(entity -> ProductDTO.builder()
            .name(null)
            .imageUrl(null)
            .description(null)
            .price(null)
            .cost(null)
            .categories(null)
            .build())
        .toList();

    dto.size();

    return null;
  }

  // -----------------------------------------------------------------------------

  @Override
  public Long update(Claims authorization, Long productId, ProductDTO product) {

    String permissionToken = authorization.get("UPDATE_PRODUCT", String.class);
    if (permissionToken == null) {
      throw new InsufficientPermissionException("Not allowed to update products");
    }

    Product entity = productRepository.findByIdAndTenantId(productId, Long.parseLong(authorization.getSubject()));
    if (entity == null) {
      return create(authorization, product);
    }

    entity.setName(product.getName());
    entity.setImageUrl(product.getImageUrl());
    entity.setDescription(product.getDescription());
    entity.setPrice(product.getPrice());
    entity.setCost(product.getCost());

    Collection<ProductCategory> categories = product.getCategories().stream()
        .map(id -> productCategoryRepository.getReferenceById(id)).toList();

    entity.setCategories(categories);

    productRepository.save(entity);

    return productId;
  }

  // -----------------------------------------------------------------------------

  @Override
  public void deleteById(Claims authorization, Long productId) {

    String permissionToken = authorization.get("DELETE_PRODUCT", String.class);
    if (permissionToken == null) {
      throw new InsufficientPermissionException("Not allowed to delete products");
    }

    productRepository.deleteByIdAndTenantId(productId, Long.parseLong(authorization.getSubject()));
  }

  // -----------------------------------------------------------------------------

  @Override
  public void deleteAll(Claims authorization, Collection<Long> productIdList) {

    String permissionToken = authorization.get("DELETE_PRODUCT", String.class);
    if (permissionToken == null) {
      throw new InsufficientPermissionException("Not allowed to delete products");
    }

    productRepository.deleteAllByIdAndTenantId(productIdList, Long.parseLong(authorization.getSubject()));
  }

}
