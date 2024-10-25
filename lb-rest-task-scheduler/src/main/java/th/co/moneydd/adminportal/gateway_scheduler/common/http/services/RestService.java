package th.co.moneydd.adminportal.gateway_scheduler.common.http.services;

import org.springframework.http.MediaType;
import org.springframework.retry.support.RetryTemplate;

public interface RestService {

  <T> T get(String path, Class<T> responseType);

  <T, R> R post(String path, MediaType contentType, T requestBody, Class<R> responseType);

  <T, R> R put(String path, MediaType contentType, T requestBody, Class<R> responseType);

  <T> T delete(String path, Class<T> responseType);

  <T, R> R patch(String path, MediaType contentType, T requestBody, Class<R> responseType);

  // ---------------------------------------------------------------------------//

  <T> T get(String path, Class<T> responseType, RetryTemplate withRetry);

  <T, R> R post(String path, MediaType contentType, T requestBody, Class<R> responseType, RetryTemplate withRetry);

  <T, R> R put(String path, MediaType contentType, T requestBody, Class<R> responseType, RetryTemplate withRetry);

  <T> T delete(String path, Class<T> responseType, RetryTemplate withRetry);

  <T, R> R patch(String path, MediaType contentType, T requestBody, Class<R> responseType, RetryTemplate withRetry);

}
