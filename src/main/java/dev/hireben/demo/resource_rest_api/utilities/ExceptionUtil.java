package dev.hireben.demo.resource_rest_api.utilities;

import dev.hireben.demo.resource_rest_api.exceptions.SeverityLevel;

public class ExceptionUtil {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final String DEBUG_STRING_FORMAT = "%s[code=%s, severity=%s, message=%s]";
  private static final String DEBUG_LOG_FORMAT = "Trace: %s >>> %s";

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public static String formatDebugString(Class<? extends Throwable> clazz, String code, SeverityLevel severity,
      String message) {
    return String.format(DEBUG_STRING_FORMAT, clazz.getName(), code, severity.name(), message);
  }

  // ---------------------------------------------------------------------------//

  public static String formatTraceLog(String traceId, String message) {
    return String.format(DEBUG_LOG_FORMAT, traceId, message);
  };

}
