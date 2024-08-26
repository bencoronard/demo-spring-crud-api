package dev.hireben.demo.crud_api.product;

import dev.hireben.demo.common_libs.exception.ApplicationException;

public final class ProductNotFoundException extends ApplicationException {

  ProductNotFoundException(String message) {
    super(message);
  }

}
