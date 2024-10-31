package th.co.loxbit.adminportal.gateway_scheduler.common.handlers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import th.co.loxbit.adminportal.gateway_scheduler.common.dtos.ExceptionData;
import th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.WrappingException;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.dtos.responses.GlobalResponseBody;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.exceptions.HttpServiceException;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.exceptions.RetryableHttpServiceException;
import th.co.loxbit.adminportal.gateway_scheduler.common.utilities.EnvironmentUtil;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.exceptions.TaskExecutionException;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.exceptions.JobArgumentException;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.exceptions.JobNotDeletableException;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.exceptions.JobNotFoundException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final EnvironmentUtil environment;

  private static final String TRACE_ID_KEY = "TRACE_ID";

  // ---------------------------------------------------------------------------//
  // FIELDS
  // ---------------------------------------------------------------------------//

  private static final Map<Class<? extends Throwable>, HttpStatus> EXCEPTION_STATUS_MAP = Map.of(
      HttpServiceException.class, HttpStatus.BAD_GATEWAY,
      RetryableHttpServiceException.class, HttpStatus.BAD_GATEWAY,
      JobArgumentException.class, HttpStatus.BAD_REQUEST,
      JobNotDeletableException.class, HttpStatus.FORBIDDEN,
      JobNotFoundException.class, HttpStatus.NOT_FOUND);

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @ExceptionHandler(WrappingException.class)
  public ResponseEntity<GlobalResponseBody<String>> handleWrappingException(
      WrappingException exception,
      @RequestAttribute(TRACE_ID_KEY) String traceId) {

    ExceptionData exceptionData = exception.getExceptionData();
    Class<? extends Throwable> exceptionClass = exceptionData.getExceptionClass();

    String debugMsg = "Exception " + exceptionClass + " thrown with message: " + exceptionData.getDebugMsg();

    log.error("Trace: " + traceId + " >>> " + debugMsg);

    GlobalResponseBody.GlobalResponseBodyBuilder<String> builder = GlobalResponseBody.<String>builder();
    builder.respCode(exceptionData.getRespCode());
    if (environment.isDevEnvironment()) {
      builder.desc(debugMsg);
    }
    builder.payload(exceptionData.getRespMsg());

    HttpStatus respStatus = EXCEPTION_STATUS_MAP.getOrDefault(exceptionClass, HttpStatus.INTERNAL_SERVER_ERROR);

    return new ResponseEntity<>(builder.build(), respStatus);
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(TaskExecutionException.class)
  public void handleTaskExecutionException(TaskExecutionException exception) {

    ExceptionData exceptionData = ((WrappingException) exception.getCause()).getExceptionData();

    String errorMsg = "Exception " + exceptionData.getExceptionClass() + " thrown during job execution with message: "
        + exceptionData.getDebugMsg();

    log.error(errorMsg);
  }

}
