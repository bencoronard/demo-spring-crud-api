package th.co.loxbit.rest_task_scheduler.gateway.services.implementations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import th.co.loxbit.rest_task_scheduler.common.factories.ConfigurableObjectFactory;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.http.requests.CloseGatewayRequest;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.http.responses.CloseGatewayResponse;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.http.responses.OpenGatewayResponse;
import th.co.loxbit.rest_task_scheduler.gateway.services.GatewayService;
import th.co.loxbit.rest_task_scheduler.gateway.utilities.GatewayStatus;
import th.co.loxbit.rest_task_scheduler.http.configurers.RequestInterceptorConfigurer;
import th.co.loxbit.rest_task_scheduler.http.configurers.RestServiceConfigurer;
import th.co.loxbit.rest_task_scheduler.http.exceptions.ExternalServerErrorException;
import th.co.loxbit.rest_task_scheduler.http.interceptors.RequestInterceptor;
import th.co.loxbit.rest_task_scheduler.http.services.RestService;
import th.co.loxbit.rest_task_scheduler.http.services.implementations.RestServiceImpl;

@Service
public class GatewayServiceImpl implements GatewayService {

  private final RestService restService;

  public GatewayServiceImpl(
    ConfigurableObjectFactory<RequestInterceptor, RequestInterceptorConfigurer> interceptorFactory,
    ConfigurableObjectFactory<RestServiceImpl, RestServiceConfigurer> restServiceFactory,
    @Value("${api.external.gateway.secret.key}") String apiKey,
    @Value("${api.external.gateway.uri}") String baseUrl
  ) {
    RequestInterceptor interceptor = interceptorFactory.create(
      RequestInterceptorConfigurer.builder()
                                  .apiKey(apiKey)
                                  .build()
    );
    this.restService = restServiceFactory.create(
      RestServiceConfigurer.builder()
                           .baseUrl(baseUrl)
                           .interceptor(interceptor)
                           .build()
    );
  }

  @Override
  public GatewayStatus getGatewayStatus() {
    // restService.get("/", GetGatewayStatusResponse.class);
    throw ExternalServerErrorException.builder()
                                      .serviceCode(SERVICE_CODE)
                                      .sectionCode(100)
                                      .errorCode(90)
                                      .message("Hello, world!")
                                      .build();
    // return GatewayStatus.OPEN;
  }

  @Override
  public void openGateway() {
    restService.post("/", null, OpenGatewayResponse.class);
  }

  @Override
  public void closeGateway(String maintenanceMsg) {
    CloseGatewayRequest requestBody = CloseGatewayRequest.builder()
                                                         .maintenanceMessage(maintenanceMsg)
                                                         .build();
    restService.post("/", requestBody, CloseGatewayResponse.class);
  }

}
