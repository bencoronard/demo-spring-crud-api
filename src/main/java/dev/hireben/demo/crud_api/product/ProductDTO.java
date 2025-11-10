package dev.hireben.demo.crud_api.product;

import java.math.BigDecimal;
import java.util.Collection;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductDTO {
  String name;
  String imageUrl;
  String description;
  BigDecimal price;
  BigDecimal cost;
  Collection<Long> categories;
}
