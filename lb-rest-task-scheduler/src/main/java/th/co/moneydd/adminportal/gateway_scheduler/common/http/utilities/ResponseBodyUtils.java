package th.co.moneydd.adminportal.gateway_scheduler.common.http.utilities;

import th.co.moneydd.adminportal.gateway_scheduler.common.http.dtos.responses.GlobalResponseBody;

public class ResponseBodyUtils {

  // ---------------------------------------------------------------------------//
  // Methods
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
