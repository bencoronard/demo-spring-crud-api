package th.co.moneydd.adminportal.gateway_scheduler.common.http.handlers;

import java.io.IOException;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import th.co.moneydd.adminportal.gateway_scheduler.common.http.exceptions.HttpServiceException;
import th.co.moneydd.adminportal.gateway_scheduler.common.http.exceptions.RetryableHttpServiceException;

@Component
public class RestErrorResponseHandler implements ResponseErrorHandler {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final Set<HttpStatus> RETRYABLE_STATUSES = Set.of(
      HttpStatus.REQUEST_TIMEOUT,
      HttpStatus.INTERNAL_SERVER_ERROR,
      HttpStatus.BAD_GATEWAY,
      HttpStatus.SERVICE_UNAVAILABLE,
      HttpStatus.GATEWAY_TIMEOUT);

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public boolean hasError(@NonNull ClientHttpResponse response) throws IOException {
    return response.getStatusCode().isError();
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void handleError(@NonNull ClientHttpResponse response) throws IOException {

    HttpStatusCode statusCode = response.getStatusCode();
    String body = new String(response.getBody().readAllBytes());

    if (RETRYABLE_STATUSES.contains(statusCode)) {
      throw new RetryableHttpServiceException("Retryable HTTP error: " + statusCode + ", Body: " + body);
    }

    throw new HttpServiceException("HTTP error: " + statusCode + ", Body: " + body);
  }

}
