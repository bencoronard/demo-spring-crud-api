package dev.hireben.demo.crud_api.common.handler;

import java.util.Map;

import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import dev.hireben.demo.common_libs.http.handler.HttpGlobalExceptionHandler;
import dev.hireben.demo.crud_api.product.exception.ProductNotFoundException;
import io.micrometer.tracing.Tracer;

@RestControllerAdvice
final class GlobalExceptionHandler extends HttpGlobalExceptionHandler {

  GlobalExceptionHandler(Tracer tracer) {
    super(tracer);
    exceptionStatusMap.putAll(statusMap);
  }

  // =============================================================================

  private static final Map<Class<? extends Throwable>, HttpStatus> statusMap = Map.of(
      ProductNotFoundException.class, HttpStatus.NOT_FOUND);

  // =============================================================================

  @ExceptionHandler({
      OptimisticLockingFailureException.class,
      PessimisticLockingFailureException.class
  })
  private ResponseEntity<Object> handleConcurrencyFailure(
      ConcurrencyFailureException ex,
      WebRequest request) {

    HttpStatus status = HttpStatus.CONFLICT;

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, "Database update fail. Try again later.");

    return createResponseEntity(problemDetail, HttpHeaders.EMPTY, status, request);
  }

}
