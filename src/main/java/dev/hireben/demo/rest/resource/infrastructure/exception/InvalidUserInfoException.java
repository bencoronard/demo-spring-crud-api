package dev.hireben.demo.rest.resource.infrastructure.exception;

public class InvalidUserInfoException extends RuntimeException {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public InvalidUserInfoException(String message) {
    super(message);
  }

}
