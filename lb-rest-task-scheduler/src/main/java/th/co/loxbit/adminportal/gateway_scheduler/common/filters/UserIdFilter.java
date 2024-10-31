package th.co.loxbit.adminportal.gateway_scheduler.common.filters;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserIdFilter extends OncePerRequestFilter {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final String USER_ID_HEADER = "X-USER-ID";
  private static final String USER_POS_HEADER = "X-USER-POSITION";
  private static final String USER_ROLE_HEADER = "X-USER-ROLE";
  private static final String TRACE_ID_HEADER = "trace-id";

  private static final String USER_ID_KEY = "USER_ID";
  private static final String USER_POS_KEY = "USER_POS";
  private static final String USER_ROLE_KEY = "USER_ROLE";
  private static final String TRACE_ID_KEY = "TRACE_ID";

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    String userId = request.getHeader(USER_ID_HEADER);
    if (!containHeader(userId)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing user ID");
      return;
    }

    String userPosition = request.getHeader(USER_POS_HEADER);
    if (!containHeader(userPosition)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing user position");
      return;
    }

    String userRole = request.getHeader(USER_ROLE_HEADER);
    if (!containHeader(userRole)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing user role");
      return;
    }

    String traceId = request.getHeader(TRACE_ID_HEADER);
    if (!containHeader(traceId)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing trace ID");
      return;
    }

    request.setAttribute(USER_ID_KEY, userId);
    request.setAttribute(USER_POS_KEY, userPosition);
    request.setAttribute(USER_ROLE_KEY, userRole);
    request.setAttribute(TRACE_ID_KEY, traceId);

    filterChain.doFilter(request, response);
  }

  // ---------------------------------------------------------------------------//

  private boolean containHeader(String headerValue) {
    return headerValue != null && !headerValue.isBlank();
  }

}
