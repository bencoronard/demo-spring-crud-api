package dev.hireben.demo.resource_rest_api.anOldStruct.exceptions.handlers;

import java.io.IOException;
import java.util.ArrayList;
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

import dev.hireben.demo.resource_rest_api.anOldStruct.common.DefaultValue;
import dev.hireben.demo.resource_rest_api.anOldStruct.common.dtos.FieldValidationExceptionMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final Map<Class<? extends Throwable>, HttpStatus> EXCEPTION_STATUS_MAP = Map
      .of();

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @ExceptionHandler(CustomException.class)
  public void handleCustomException(
      CustomException exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    Class<? extends Throwable> errorClass = exception.getClass();
    String errorCode = String.valueOf(exception.getErrorCode());
    SeverityLevel severity = exception.getSeverity();
    String errorMsg = exception.getMessage();

    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_CODE, errorCode);
    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_MSG, errorMsg);
    request.setAttribute(RequestAttributeKey.EXCEPTION_SEVERITY, severity);

    HttpStatus status = EXCEPTION_STATUS_MAP.getOrDefault(errorClass, HttpStatus.INTERNAL_SERVER_ERROR);

    response.sendError(status.value(), errorMsg);
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(DataIntegrityViolationException.class)
  public void handleDataIntegrityViolationException(DataIntegrityViolationException exception,
      HttpServletRequest request, HttpServletResponse response) throws IOException {

    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_CODE, "4004");
    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_MSG, "Data integrity violation");
    request.setAttribute(RequestAttributeKey.EXCEPTION_SEVERITY, SeverityLevel.LOW);

    response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public void handleFieldValidationException(MethodArgumentNotValidException exception, HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    Collection<ObjectError> validationErrors = exception.getBindingResult().getAllErrors();

    Collection<FieldValidationExceptionMap> validationErrorMaps = new ArrayList<>(validationErrors.size());

    validationErrors.forEach(validationError -> validationErrorMaps.add(FieldValidationExceptionMap.builder()
        .field(((FieldError) validationError).getField())
        .message(validationError.getDefaultMessage())
        .build()));

    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_CODE, "9669");
    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_MSG, "Field validation errors");
    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_DATA, validationErrorMaps);
    request.setAttribute(RequestAttributeKey.EXCEPTION_SEVERITY, SeverityLevel.MEDIUM);

    response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(ConstraintViolationException.class)
  public void handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    Collection<ConstraintViolation<?>> violationErrors = exception.getConstraintViolations();

    Collection<FieldValidationExceptionMap> violationErrorMaps = new ArrayList<>(violationErrors.size());

    violationErrors.forEach(violationError -> violationErrorMaps.add(FieldValidationExceptionMap.builder()
        .field(violationError.getPropertyPath().toString())
        .message(violationError.getMessage())
        .build()));

    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_CODE, "9670");
    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_MSG, "Constraint violation errors");
    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_DATA, violationErrorMaps);
    request.setAttribute(RequestAttributeKey.EXCEPTION_SEVERITY, SeverityLevel.LOW);

    response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public void handleHttpMessageNotReadableException(
      HttpMessageNotReadableException exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_CODE, DefaultValue.RESP_CODE_UNKNOWN);
    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_MSG, "Required request body is missing or unreadable");
    request.setAttribute(RequestAttributeKey.EXCEPTION_SEVERITY, SeverityLevel.LOW);

    response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(NoResourceFoundException.class)
  public void handleNoResourceFoundException(NoResourceFoundException exception, HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_CODE, DefaultValue.RESP_CODE_UNKNOWN);
    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_MSG, DefaultValue.DEFAULT_ERR_RESP_MSG);
    request.setAttribute(RequestAttributeKey.EXCEPTION_SEVERITY, SeverityLevel.HIGH);

    response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(Exception.class)
  public void handleException(
      Exception exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_CODE, DefaultValue.RESP_CODE_UNKNOWN);
    request.setAttribute(RequestAttributeKey.EXCEPTION_RESP_MSG, DefaultValue.DEFAULT_ERR_RESP_MSG);
    request.setAttribute(RequestAttributeKey.EXCEPTION_SEVERITY, SeverityLevel.HIGH);

    response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
  }

}
