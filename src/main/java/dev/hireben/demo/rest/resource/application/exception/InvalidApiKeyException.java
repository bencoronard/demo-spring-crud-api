package dev.hireben.demo.rest.resource.application.exception;

public class InvalidApiKeyException extends RuntimeException {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public InvalidApiKeyException(String message) {
    super(message);
  }

}
