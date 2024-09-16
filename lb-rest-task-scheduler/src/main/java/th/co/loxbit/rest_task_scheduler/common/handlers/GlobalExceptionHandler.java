package th.co.loxbit.rest_task_scheduler.common.handlers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.common.dtos.ExceptionData;
import th.co.loxbit.rest_task_scheduler.common.exceptions.WrappingException;
import th.co.loxbit.rest_task_scheduler.common.http.dtos.responses.GlobalResponseBody;
import th.co.loxbit.rest_task_scheduler.common.http.exceptions.HttpServiceException;
import th.co.loxbit.rest_task_scheduler.common.http.exceptions.RetryableHttpServiceException;
import th.co.loxbit.rest_task_scheduler.common.utilities.EnvironmentUtil;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final EnvironmentUtil environment;

  // ---------------------------------------------------------------------------//
  // FIELDS
  // ---------------------------------------------------------------------------//

  private static final Map<Class<? extends Throwable>, HttpStatus> EXCEPTION_STATUS_MAP = Map.of(
      HttpServiceException.class, HttpStatus.BAD_GATEWAY,
      RetryableHttpServiceException.class, HttpStatus.BAD_GATEWAY);

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @ExceptionHandler(WrappingException.class)
  public ResponseEntity<GlobalResponseBody<String>> handleWrappingException(WrappingException exception) {

    ExceptionData exData = exception.getRespData();
    Class<? extends Throwable> exClass = exData.getExceptionClass();

    GlobalResponseBody.GlobalResponseBodyBuilder<String> builder = GlobalResponseBody.<String>builder();

    builder.respCode(exData.getRespCode());

    if (environment.isDevEnvironment()) {
      builder.desc("Exception: " + exClass + " thrown with message: " + exData.getErrorMsg());
    }

    builder.payload(exData.getRespMsg());

    HttpStatus respStatus = EXCEPTION_STATUS_MAP.getOrDefault(exClass, HttpStatus.INTERNAL_SERVER_ERROR);

    return new ResponseEntity<>(builder.build(), respStatus);
  }

}
