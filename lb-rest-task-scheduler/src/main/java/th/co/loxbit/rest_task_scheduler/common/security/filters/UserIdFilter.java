package th.co.loxbit.rest_task_scheduler.common.security.filters;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    if (!isValidUserId(userId)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing user ID");
      return;
    }

    Authentication auth = new UsernamePasswordAuthenticationToken(userId, null, null);

    SecurityContextHolder.getContext().setAuthentication(auth);

    filterChain.doFilter(request, response);

  }

  // ---------------------------------------------------------------------------//

  private boolean isValidUserId(String userId) {
    return userId != null && !userId.isBlank();
  }

}
