package dev.hireben.demo.resource_rest_api.filters;

import java.io.IOException;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.hireben.demo.resource_rest_api.context.HttpHeaderKey;
import dev.hireben.demo.resource_rest_api.context.RequestAttributeKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpHeaderFilter extends OncePerRequestFilter {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String API_KEY;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    if (!hasValidApiKey(request)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid API key");
      return;
    }

    String traceId = extractTraceId(request);
    if (traceId != null) {
      request.setAttribute(RequestAttributeKey.TRACE_ID, traceId);
    }

    String serviceId = extractServiceId(request);
    if (serviceId == null) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or unregistered service ID");
      return;
    }

    request.setAttribute(RequestAttributeKey.ORIGIN_ID, serviceId);

    filterChain.doFilter(request, response);
  }

  // ---------------------------------------------------------------------------//
  // Helpers
  // ---------------------------------------------------------------------------//

  private boolean hasValidApiKey(HttpServletRequest request) {
    String reqApiKey = request.getHeader(HttpHeaderKey.API_KEY);
    return reqApiKey != null && API_KEY.equals(reqApiKey);
  }

  // ---------------------------------------------------------------------------//

  private String extractTraceId(HttpServletRequest request) {
    String reqTraceId = request.getHeader(HttpHeaderKey.TRACE_ID);
    return reqTraceId != null && !reqTraceId.isBlank() ? reqTraceId : null;
  }

  // ---------------------------------------------------------------------------//

  private String extractServiceId(HttpServletRequest request) {
    String reqServId = request.getHeader(HttpHeaderKey.SERVICE_ID);
    return reqServId != null && !reqServId.isBlank() ? reqServId : null;
  }

}
