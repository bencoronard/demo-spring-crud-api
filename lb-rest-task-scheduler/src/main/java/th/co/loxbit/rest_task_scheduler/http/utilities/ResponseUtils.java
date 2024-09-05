package th.co.loxbit.rest_task_scheduler.http.utilities;

import th.co.loxbit.rest_task_scheduler.common.exceptions.BaseException;
import th.co.loxbit.rest_task_scheduler.http.responses.GlobalResponseBody;

public class ResponseUtils {

  public static GlobalResponseBody<String> createErrorResponseBody(
      BaseException exception) {
    Throwable cause = exception.getCause();
    String causeMessage = cause.getMessage();
    String message = exception.getMessage();
    String payload;
    if (cause != null && causeMessage != null) {
      payload = causeMessage;
    } else if (message != null) {
      payload = message;
    } else {
      payload = null;
    }
    return GlobalResponseBody.<String>builder()
        .respCode(exception.getExitCode())
        .desc("Runtime error")
        .payload(payload)
        .build();
  }

  public static <T> GlobalResponseBody<T> createSuccessResponseBody(
      String desc,
      T payload) {
    return GlobalResponseBody.<T>builder()
        .respCode("0000")
        .desc(desc)
        .payload(payload)
        .build();
  }

}
