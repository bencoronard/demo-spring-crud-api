package th.co.loxbit.rest_task_scheduler.common.http.utilities;

import th.co.loxbit.rest_task_scheduler.common.exceptions.ServiceRuntimeException;
import th.co.loxbit.rest_task_scheduler.common.http.responses.GlobalResponseBody;

public class ResponseBodyUtils {

  public static GlobalResponseBody<String> createErrorResponseBody(
      ServiceRuntimeException exception) {
    String errorMessage = exception.getMessage();
    return GlobalResponseBody.<String>builder()
        .respCode(exception.getRespCode())
        .desc("runtime exception")
        .payload(errorMessage != null ? errorMessage : "An exception was thrown")
        .build();
  }

  public static <T> GlobalResponseBody<T> createSuccessResponseBody(
      String desc,
      T payload) {
    return GlobalResponseBody.<T>builder()
        .respCode(0)
        .desc(desc)
        .payload(payload)
        .build();
  }

}
