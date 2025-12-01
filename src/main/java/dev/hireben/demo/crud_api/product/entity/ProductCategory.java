package dev.hireben.demo.crud_api.product.entity;

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
public final class ProductCategory extends ResourceEntity {
  private String name;
}
