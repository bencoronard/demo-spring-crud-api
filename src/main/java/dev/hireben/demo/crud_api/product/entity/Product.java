package dev.hireben.demo.crud_api.product.entity;

import java.math.BigDecimal;
import java.util.Collection;
import dev.hireben.demo.crud_api.common.entity.ResourceEntity;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public final class Product extends ResourceEntity {

  private String name;

  private String imageUrl;

  private String description;

  private BigDecimal price;

  private BigDecimal cost;

  private Long tenantId;

  private Collection<ProductCategory> categories;
}
