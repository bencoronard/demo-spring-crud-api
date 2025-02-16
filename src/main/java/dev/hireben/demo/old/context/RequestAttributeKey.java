package dev.hireben.demo.old.context;

public final class RequestAttributeKey {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  public static final String TRACE_ID = "TRX_ID";

  public static final String EXCEPTION_RESP_CODE = "ERR_RESP_CODE";
  public static final String EXCEPTION_RESP_MSG = "ERR_RESP_MSG";
  public static final String EXCEPTION_RESP_DATA = "ERR_RESP_DATA";
  public static final String EXCEPTION_SEVERITY = "ERR_SEV";

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  private RequestAttributeKey() {
  };

}
