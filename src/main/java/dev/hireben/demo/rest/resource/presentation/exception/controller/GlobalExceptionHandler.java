package dev.hireben.demo.rest.resource.presentation.exception.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import dev.hireben.demo.rest.resource.application.exception.InvalidUserInfoException;
import dev.hireben.demo.rest.resource.application.exception.ResourceNotFoundException;
import dev.hireben.demo.rest.resource.presentation.exception.model.SeverityLevel;
import dev.hireben.demo.rest.resource.presentation.model.DefaultValue;
import dev.hireben.demo.rest.resource.presentation.model.RequestAttributeKey;
import dev.hireben.demo.rest.resource.presentation.response.FieldValidationErrorMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final Map<Class<? extends Throwable>, HttpStatus> EXCEPTION_STATUS_MAP = Map.of(
      ResourceNotFoundException.class, HttpStatus.NOT_FOUND,
      InvalidUserInfoException.class, HttpStatus.BAD_REQUEST);

  private static final Map<Class<? extends Throwable>, String> EXCEPTION_CODE_MAP = Map.of(
      ResourceNotFoundException.class, "1404",
      InvalidUserInfoException.class, "2401");

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @ExceptionHandler({ ResourceNotFoundException.class, InvalidUserInfoException.class })
  public void handleMvcException(
      Exception exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    Class<? extends Throwable> errorClass = exception.getClass();

    String code = EXCEPTION_CODE_MAP.getOrDefault(errorClass, DefaultValue.RESP_CODE_UNKNOWN);
    String message = exception.getMessage();
    HttpStatus status = EXCEPTION_STATUS_MAP.getOrDefault(errorClass, HttpStatus.INTERNAL_SERVER_ERROR);

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, code);
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG, message);
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.LOW);

    response.sendError(status.value(), message);
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(DataIntegrityViolationException.class)
  public void handleDataIntegrityViolationException(
      DataIntegrityViolationException exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, "3001");
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG, "Data integrity violation");
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.LOW);

    response.sendError(HttpStatus.CONFLICT.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public void handleFieldValidationException(
      MethodArgumentNotValidException exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    Collection<ObjectError> validationErrors = exception.getBindingResult().getAllErrors();

    Collection<FieldValidationErrorMap> validationErrorMaps = validationErrors.stream()
        .map(validationError -> FieldValidationErrorMap.builder()
            .field(((FieldError) validationError).getField())
            .message(validationError.getDefaultMessage())
            .build())
        .toList();

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, "4000");
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG, "Field validation errors");
    request.setAttribute(RequestAttributeKey.ERR_RESP_DATA, validationErrorMaps);
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.LOW);

    response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(ConstraintViolationException.class)
  public void handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    Collection<ConstraintViolation<?>> violationErrors = exception.getConstraintViolations();

    Collection<FieldValidationErrorMap> violationErrorMaps = violationErrors.stream()
        .map(violationError -> FieldValidationErrorMap.builder()
            .field(violationError.getPropertyPath().toString())
            .message(violationError.getMessage())
            .build())
        .toList();

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, "4001");
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG, "Constraint violation errors");
    request.setAttribute(RequestAttributeKey.ERR_RESP_DATA, violationErrorMaps);
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.LOW);

    response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public void handleHttpMessageNotReadableException(
      HttpMessageNotReadableException exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, DefaultValue.RESP_CODE_UNKNOWN);
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG, "Required request body is missing or unreadable");
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.LOW);

    response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(NoResourceFoundException.class)
  public void handleNoResourceFoundException(
      NoResourceFoundException exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, "4404");
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG,
        String.format("Endpoint %s %s not supported", exception.getHttpMethod(), exception.getResourcePath()));
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.LOW);

    response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(Exception.class)
  public void handleException(
      Exception exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, DefaultValue.RESP_CODE_UNKNOWN);
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG, DefaultValue.RESP_MSG_UNKNOWN);
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.HIGH);

    response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
  }

}
