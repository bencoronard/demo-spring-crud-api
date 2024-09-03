package th.co.loxbit.rest_task_scheduler.common.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import th.co.loxbit.rest_task_scheduler.http.responses.GlobalResponseBody;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<GlobalResponseBody<String>> handleRuntimeException(RuntimeException exception) {
    GlobalResponseBody<String> responseBody = GlobalResponseBody.<String>builder()
                                                                .exitCode("0000")
                                                                .desc("Error")
                                                                .payload(exception.getMessage())
                                                                .build();
    return new ResponseEntity<>(responseBody, HttpStatus.I_AM_A_TEAPOT);
  }

}
