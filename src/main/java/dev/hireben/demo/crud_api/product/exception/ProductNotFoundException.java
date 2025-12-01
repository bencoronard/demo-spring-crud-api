package dev.hireben.demo.crud_api.product.exception;

import dev.hireben.demo.common_libs.exception.ApplicationException;

public final class ProductNotFoundException extends ApplicationException {

  public ProductNotFoundException(String message) {
    super(message);
  }

}
