package dev.hireben.demo.resource_rest_api.exceptions;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String errorCode;
  private final SeverityLevel severity;

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  protected CustomException(String errorCode, String message, SeverityLevel severity) {
    super(message);
    this.errorCode = errorCode;
    this.severity = severity;
  }

}
