package dev.hireben.demo.resource_rest_api.anOldStruct.filters;

import java.io.IOException;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

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

    request.setAttribute(RequestAttributeKey.TRACE_ID, extractTraceId(request));

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

}
