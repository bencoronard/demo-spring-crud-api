package dev.hireben.demo.rest.resource.infrastructure.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DefaultValue {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  public final String RESP_CODE_SUCCESS = "0000";
  public final String RESP_CODE_UNKNOWN = "9999";

  public final String RESP_MSG_UNKNOWN = "Internal server error";
  public final String TRACE_ID_UNKNOWN = "null-trx-id";

}
