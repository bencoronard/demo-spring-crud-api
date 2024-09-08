package th.co.loxbit.rest_task_scheduler.common.http.handlers;

import java.io.IOException;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import th.co.loxbit.rest_task_scheduler.common.http.exceptions.Resp4xxException;
import th.co.loxbit.rest_task_scheduler.common.http.exceptions.Resp5xxException;

@Component
public class RestErrorResponseHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(@NonNull ClientHttpResponse response) throws IOException {

    HttpStatusCode responseStatusCode = response.getStatusCode();

    return responseStatusCode.is4xxClientError() || responseStatusCode.is5xxServerError();
  }

  @Override
  public void handleError(@NonNull ClientHttpResponse response) throws IOException {

    HttpStatusCode responseStatusCode = response.getStatusCode();

    if (responseStatusCode.is4xxClientError()) {
      throw new Resp4xxException(0, 0, "Client error");
    } else {
      throw new Resp5xxException(0, 0, "Server error");
    }

  }

}
