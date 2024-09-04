package th.co.loxbit.rest_task_scheduler.common.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import th.co.loxbit.rest_task_scheduler.common.exceptions.CatchAllException;
import th.co.loxbit.rest_task_scheduler.http.exceptions.ExternalServiceException;
import th.co.loxbit.rest_task_scheduler.http.responses.GlobalResponseBody;
import th.co.loxbit.rest_task_scheduler.http.utilities.ResponseUtils;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ExternalServiceException.class)
  public ResponseEntity<GlobalResponseBody<String>> handleExternalServerErrorException(
      ExternalServiceException exception) {
    return new ResponseEntity<>(ResponseUtils.createErrorResponseBody(exception), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(CatchAllException.class)
  public ResponseEntity<GlobalResponseBody<String>> handleCatchAllException(CatchAllException exception) {
    return new ResponseEntity<>(ResponseUtils.createErrorResponseBody(exception), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
