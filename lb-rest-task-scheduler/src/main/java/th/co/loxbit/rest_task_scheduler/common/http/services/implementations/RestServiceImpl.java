package th.co.loxbit.rest_task_scheduler.common.http.services.implementations;

import org.springframework.http.MediaType;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.common.http.services.RestService;

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
    return this.restClient
        .post()
        .uri(path)
        .contentType(MediaType.APPLICATION_JSON)
        .body(requestBody)
        .retrieve()
        .body(responseType);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public <T, R> R put(String path, MediaType contentType, T requestBody, Class<R> responseType) {
    return this.restClient
        .put()
        .uri(path)
        .contentType(MediaType.APPLICATION_JSON)
        .body(requestBody)
        .retrieve()
        .body(responseType);
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
  public <T> T get(String path, Class<T> responseType, RetryTemplate withRetry) {
    return withRetry.execute((context) -> {
      return this.restClient
          .get()
          .uri(path)
          .retrieve()
          .body(responseType);
    });
  }

  // ---------------------------------------------------------------------------//

  @Override
  public <T, R> R post(String path, MediaType contentType, T requestBody, Class<R> responseType,
      RetryTemplate withRetry) {
    return withRetry.execute((context) -> {
      return this.restClient
          .post()
          .uri(path)
          .contentType(MediaType.APPLICATION_JSON)
          .body(requestBody)
          .retrieve()
          .body(responseType);
    });
  }

  // ---------------------------------------------------------------------------//

  @Override
  public <T, R> R put(String path, MediaType contentType, T requestBody, Class<R> responseType,
      RetryTemplate withRetry) {
    return withRetry.execute((context) -> {
      return this.restClient
          .put()
          .uri(path)
          .contentType(MediaType.APPLICATION_JSON)
          .body(requestBody)
          .retrieve()
          .body(responseType);
    });
  }

  // ---------------------------------------------------------------------------//

  @Override
  public <T> T delete(String path, Class<T> responseType, RetryTemplate withRetry) {
    return withRetry.execute((context) -> {
      return this.restClient
          .delete()
          .uri(path)
          .retrieve()
          .body(responseType);
    });
  }

}
