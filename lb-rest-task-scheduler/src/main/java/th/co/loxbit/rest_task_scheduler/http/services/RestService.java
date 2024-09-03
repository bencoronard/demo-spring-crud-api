package th.co.loxbit.rest_task_scheduler.http.services;

public interface RestService {

  <T> T get(String url, Class<T> responseType);

  <T, R> R post(String url, T requestBody, Class<R> responseType);

  <T, R> R put(String url, T requestBody, Class<R> responseType);

  <T> T delete(String url, Class<T> responseType);

}
