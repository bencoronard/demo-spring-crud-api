package dev.hireben.demo.rest.resource.infrastructure.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpHeaderKey {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  public final String API_KEY = "X-Api-Key";
  public final String TRACE_ID = "X-Trace-Id";

  public final String USER_ID = "X-User-Id";
  public final String USER_TENANT = "X-User-Tenant";

}
