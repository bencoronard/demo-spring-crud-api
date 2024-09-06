package th.co.loxbit.rest_task_scheduler.common.http.interceptors;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

import th.co.loxbit.rest_task_scheduler.common.http.configurers.RequestInterceptorConfigurer;

public class RequestInterceptor implements ClientHttpRequestInterceptor {

  private final String apiKey;

  public RequestInterceptor(RequestInterceptorConfigurer config) {
    this.apiKey = config.getApiKey();
  }

  @Override
  @NonNull
  public ClientHttpResponse intercept(
      @NonNull HttpRequest request,
      @NonNull byte[] body,
      @NonNull ClientHttpRequestExecution execution) throws IOException {

    addApiKeyToHeader(request, apiKey);

    ClientHttpResponse response = execution.execute(request, body);

    return response;
  }

  private void addApiKeyToHeader(HttpRequest request, String apiKey) {
    HttpHeaders headers = request.getHeaders();
    headers.add("Authorization", "Bearer " + apiKey);
  }

}
