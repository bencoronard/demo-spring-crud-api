package th.co.loxbit.adminportal.gateway_scheduler.common.http.services.implementations;

import org.springframework.http.MediaType;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestBodySpec;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.services.RestService;

@RequiredArgsConstructor
public class RestServiceImpl implements RestService {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final RestClient restClient;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public <T> T get(String path, Class<T> responseType) {
    return this.restClient
        .get()
        .uri(path)
        .retrieve()
        .body(responseType);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public <T, R> R post(String path, MediaType contentType, T requestBody, Class<R> responseType) {

    RequestBodySpec request = this.restClient
        .post()
        .uri(path)
        .contentType(contentType);

    if (requestBody != null) {
      request.body(requestBody);
    }

    return request.retrieve().body(responseType);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public <T, R> R put(String path, MediaType contentType, T requestBody, Class<R> responseType) {

    RequestBodySpec request = this.restClient
        .put()
        .uri(path)
        .contentType(contentType);

    if (requestBody != null) {
      request.body(requestBody);
    }

    return request.retrieve().body(responseType);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public <T> T delete(String path, Class<T> responseType) {
    return this.restClient
        .delete()
        .uri(path)
        .retrieve()
        .body(responseType);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public <T, R> R patch(String path, MediaType contentType, T requestBody, Class<R> responseType) {

    RequestBodySpec request = this.restClient
        .patch()
        .uri(path)
        .contentType(contentType);

    if (requestBody != null) {
      request.body(requestBody);
    }

    return request.retrieve().body(responseType);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public <T> T get(String path, Class<T> responseType, RetryTemplate withRetry) {
    return withRetry.execute((context) -> {
      return get(path, responseType);
    });
  }

  // ---------------------------------------------------------------------------//

  @Override
  public <T, R> R post(String path, MediaType contentType, T requestBody, Class<R> responseType,
      RetryTemplate withRetry) {
    return withRetry.execute((context) -> {
      return post(path, contentType, requestBody, responseType);
    });
  }

  // ---------------------------------------------------------------------------//

  @Override
  public <T, R> R put(String path, MediaType contentType, T requestBody, Class<R> responseType,
      RetryTemplate withRetry) {
    return withRetry.execute((context) -> {
      return put(path, contentType, requestBody, responseType);
    });
  }

  // ---------------------------------------------------------------------------//

  @Override
  public <T> T delete(String path, Class<T> responseType, RetryTemplate withRetry) {
    return withRetry.execute((context) -> {
      return delete(path, responseType);
    });
  }

  // ---------------------------------------------------------------------------//

  @Override
  public <T, R> R patch(String path, MediaType contentType, T requestBody, Class<R> responseType,
      RetryTemplate withRetry) {
    return withRetry.execute((context) -> {
      return patch(path, contentType, requestBody, responseType);
    });
  }

}
