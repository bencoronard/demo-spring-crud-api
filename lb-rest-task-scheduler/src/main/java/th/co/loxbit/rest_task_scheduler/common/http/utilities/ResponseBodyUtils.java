package th.co.loxbit.rest_task_scheduler.common.http.utilities;

import th.co.loxbit.rest_task_scheduler.common.http.responses.GlobalResponseBody;

public class ResponseBodyUtils {

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public static GlobalResponseBody<String> createErrorResponseBody(RuntimeException exception) {

    String errorMessage = exception.getMessage();

    GlobalResponseBody.GlobalResponseBodyBuilder<String> builder = GlobalResponseBody.<String>builder();

    builder.desc("Runtime exception");

    builder.payload(errorMessage != null ? errorMessage : "An exception was thrown");

    return builder.build();
  }

  // ---------------------------------------------------------------------------//

  public static <T> GlobalResponseBody<T> createSuccessResponseBody(String desc, T payload) {

    GlobalResponseBody.GlobalResponseBodyBuilder<T> builder = GlobalResponseBody.<T>builder();

    builder.respCode(0);

    if (desc != null) {
      builder.desc(desc);
    }

    if (payload != null) {
      builder.payload(payload);
    }

    return builder.build();
  }

}
