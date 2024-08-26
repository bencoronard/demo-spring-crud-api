package dev.hireben.demo.crud_api.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
final class ProductTests {

  @Test
  void test() {
    Product entity = Product.builder().build();
    entity.getId();
    assertEquals(new Product(), entity);
  }

}
