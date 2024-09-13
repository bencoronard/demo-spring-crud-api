package th.co.loxbit.rest_task_scheduler.common.http.services;

import org.springframework.retry.support.RetryTemplate;

public interface RestService {

  int SERVICE_CODE = 0;

  <T> T get(String path, Class<T> responseType);

  <T, R> R post(String path, T requestBody, Class<R> responseType);

  <T, R> R put(String path, T requestBody, Class<R> responseType);

  <T> T delete(String path, Class<T> responseType);

  <T> T getWithRetry(String path, Class<T> responseType, RetryTemplate withRetry);

  <T, R> R postWithRetry(String path, T requestBody, Class<R> responseType, RetryTemplate withRetry);

  <T, R> R putWithRetry(String path, T requestBody, Class<R> responseType, RetryTemplate withRetry);

  <T> T deleteWithRetry(String path, Class<T> responseType, RetryTemplate withRetry);

}
