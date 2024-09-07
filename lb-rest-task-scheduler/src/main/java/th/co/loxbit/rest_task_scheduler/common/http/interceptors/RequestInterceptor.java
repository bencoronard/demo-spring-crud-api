package th.co.loxbit.rest_task_scheduler.common.http.interceptors;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.common.http.configurers.RequestInterceptorConfig;

@RequiredArgsConstructor
public class RequestInterceptor implements ClientHttpRequestInterceptor {

  private final RequestInterceptorConfig config;

  @Override
  @NonNull
  public ClientHttpResponse intercept(
      @NonNull HttpRequest request,
      @NonNull byte[] body,
      @NonNull ClientHttpRequestExecution execution) throws IOException {

    if (config.getApiKey() != null) {
      addApiKeyHeader(request, config.getApiKey());
    }

    return execution.execute(request, body);
  }

  private void addApiKeyHeader(HttpRequest request, String apiKey) {
    HttpHeaders headers = request.getHeaders();
    headers.add("Authorization", "Bearer " + apiKey);
  }

}
