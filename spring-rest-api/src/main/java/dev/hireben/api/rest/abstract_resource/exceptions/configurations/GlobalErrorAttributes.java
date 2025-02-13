package dev.hireben.api.rest.abstract_resource.exceptions.configurations;

import java.util.Map;
import java.util.Optional;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.hireben.api.rest.abstract_resource.common.DefaultValue;
import dev.hireben.api.rest.abstract_resource.common.dtos.GlobalResponseBody;
import dev.hireben.api.rest.abstract_resource.context.RequestAttributeKey;
import dev.hireben.api.rest.abstract_resource.exceptions.SeverityLevel;
import dev.hireben.api.rest.abstract_resource.utilities.ExceptionUtil;
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

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

    Throwable error = getError(webRequest);
    String errorMsg = getMessage(webRequest, error);

    String traceId = extractReqAttrOrDefault(
        webRequest, RequestAttributeKey.TRACE_ID, String.class, DefaultValue.DEFAULT_ERR_TRACE_ID);
    String respCode = extractReqAttrOrDefault(
        webRequest, RequestAttributeKey.EXCEPTION_RESP_CODE, String.class, DefaultValue.RESP_CODE_UNKNOWN);
    String respMsg = extractReqAttrOrDefault(
        webRequest, RequestAttributeKey.EXCEPTION_RESP_MSG, String.class, DefaultValue.DEFAULT_ERR_RESP_MSG);
    Object exceptionData = extractReqAttrOrDefault(
        webRequest, RequestAttributeKey.EXCEPTION_RESP_DATA, Object.class, null);
    SeverityLevel severity = extractReqAttrOrDefault(
        webRequest, RequestAttributeKey.EXCEPTION_SEVERITY, SeverityLevel.class, SeverityLevel.LOW);

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
        .respCode(respCode)
        .respMsg(respMsg)
        .data(exceptionData != null ? exceptionData : null)
        .build();

    return objectMapper.convertValue(errorAttributes, new TypeReference<Map<String, Object>>() {
    });
  }

  // ---------------------------------------------------------------------------//

  private <T> T extractReqAttrOrDefault(WebRequest request, String key, Class<T> type, T defaultValue) {
    return Optional
        .ofNullable(request.getAttribute(key, RequestAttributes.SCOPE_REQUEST))
        .filter(attr -> type.isInstance(attr))
        .map(type::cast)
        .orElse(defaultValue);
  }
}
