package th.co.loxbit.rest_task_scheduler.http.services.implementations;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import th.co.loxbit.rest_task_scheduler.http.configurers.RestServiceConfigurer;
import th.co.loxbit.rest_task_scheduler.http.services.RestService;

public class RestServiceImpl implements RestService {

  private final RestClient restClient;

  public RestServiceImpl(
    RestClient.Builder restClientBuilder,
    RestServiceConfigurer config
  ) {
    this.restClient = restClientBuilder
      .baseUrl(config.getBaseUrl())
      .requestInterceptor(config.getInterceptor())
      .build();
  }

  @Override
  public <T> T get(String path, Class<T> responseType) {
    return this.restClient
      .get()
      .uri(path)
      .retrieve()
      .body(responseType);
  }

  @Override
  public <T, R> R post(String path, T requestBody, Class<R> responseType) {
    return this.restClient
      .post()
      .uri(path)
      .contentType(MediaType.APPLICATION_JSON)
      .body(requestBody)
      .retrieve()
      .body(responseType);
  }

  @Override
  public <T, R> R put(String path, T requestBody, Class<R> responseType) {
    return this.restClient
      .put()
      .uri(path)
      .contentType(MediaType.APPLICATION_JSON)
      .body(requestBody)
      .retrieve()
      .body(responseType);
  }

  @Override
  public <T> T delete(String path, Class<T> responseType) {
    return this.restClient
      .delete()
      .uri(path)
      .retrieve()
      .body(responseType);
  }

}
