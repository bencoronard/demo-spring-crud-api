package th.co.loxbit.rest_task_scheduler.modules.common.http.services;

public interface RestService {

  <T> T get(String uri, Class<T> responseType);

  <T, R> R post(String uri, T requestBody, Class<R> responseType);

  <T, R> R put(String uri, T requestBody, Class<R> responseType);

  <T> T delete(String uri, Class<T> responseType);

}
