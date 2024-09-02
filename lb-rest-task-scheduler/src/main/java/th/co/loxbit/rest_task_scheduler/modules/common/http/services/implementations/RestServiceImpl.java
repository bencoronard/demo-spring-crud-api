package th.co.loxbit.rest_task_scheduler.modules.common.http.services.implementations;

import th.co.loxbit.rest_task_scheduler.modules.common.http.services.RestService;

public class RestServiceImpl implements RestService {

  // private final RestClient restClient;

  public RestServiceImpl() {}

  @Override
  public <T> T get(String uri, Class<T> responseType) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'get'");
  }

  @Override
  public <T, R> R post(String uri, T requestBody, Class<R> responseType) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'post'");
  }

  @Override
  public <T, R> R put(String uri, T requestBody, Class<R> responseType) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'put'");
  }

  @Override
  public <T> T delete(String uri, Class<T> responseType) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

}
