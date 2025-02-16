package dev.hireben.demo.resource_rest_api.anOldStruct.exceptions.configurations;

import java.util.Map;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.hireben.demo.resource_rest_api.anOldStruct.common.DefaultValue;
import dev.hireben.demo.resource_rest_api.anOldStruct.common.dtos.GlobalResponseBody;
import dev.hireben.demo.resource_rest_api.anOldStruct.utilities.EnvironmentUtil;
import dev.hireben.demo.resource_rest_api.anOldStruct.utilities.ExceptionUtil;
import dev.hireben.demo.resource_rest_api.anOldStruct.utilities.RequestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalErrorAttributes extends DefaultErrorAttributes {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ObjectMapper objectMapper;
  private final EnvironmentUtil environment;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

    Throwable error = getError(webRequest);
    String errorMsg = getMessage(webRequest, error);

    String traceId = RequestUtil.getAttribute(webRequest, RequestAttributeKey.TRACE_ID, String.class)
        .orElse(DefaultValue.DEFAULT_ERR_TRACE_ID);
    String respCode = RequestUtil.getAttribute(webRequest, RequestAttributeKey.EXCEPTION_RESP_CODE, String.class)
        .orElse(DefaultValue.RESP_CODE_UNKNOWN);
    String respMsg = RequestUtil.getAttribute(webRequest, RequestAttributeKey.EXCEPTION_RESP_MSG, String.class)
        .orElse(DefaultValue.DEFAULT_ERR_RESP_MSG);
    Object exceptionData = RequestUtil.getAttribute(webRequest, RequestAttributeKey.EXCEPTION_RESP_DATA, Object.class)
        .orElse(null);
    SeverityLevel severity = RequestUtil
        .getAttribute(webRequest, RequestAttributeKey.EXCEPTION_SEVERITY, SeverityLevel.class)
        .orElse(SeverityLevel.LOW);

    String logString = ExceptionUtil.formatTraceLog(traceId,
        ExceptionUtil.formatDebugString(error.getClass(), respCode, severity, errorMsg));

    switch (severity) {
      case HIGH:
        log.error(logString);
        break;
      case MEDIUM:
        log.warn(logString);
        break;
      default:
        log.info(logString);
        break;
    }

    GlobalResponseBody<Object> errorAttributes = GlobalResponseBody.<Object>builder()
        .code(respCode)
        .message(respMsg)
        .payload(exceptionData != null ? exceptionData : environment.isDev() ? errorMsg : null)
        .build();

    return objectMapper.convertValue(errorAttributes, new TypeReference<Map<String, Object>>() {
    });
  }

}
