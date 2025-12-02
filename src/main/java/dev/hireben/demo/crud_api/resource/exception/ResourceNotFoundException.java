package dev.hireben.demo.crud_api.resource.exception;

import dev.hireben.demo.common_libs.exception.ApplicationException;

public final class ResourceNotFoundException extends ApplicationException {

  public ResourceNotFoundException(String message) {
    super(message);
  }

}
