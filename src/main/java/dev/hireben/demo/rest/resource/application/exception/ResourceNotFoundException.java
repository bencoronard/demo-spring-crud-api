package dev.hireben.demo.rest.resource.application.exception;

public class ResourceNotFoundException extends RuntimeException {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public ResourceNotFoundException(String message) {
    super(message);
  }

}
