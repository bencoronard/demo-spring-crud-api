package th.co.loxbit.rest_task_scheduler.gateway.services.implementations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import th.co.loxbit.rest_task_scheduler.common.exceptions.CatchAllException;
import th.co.loxbit.rest_task_scheduler.common.factories.ConfigurableObjectFactory;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.http.requests.CloseGatewayRequest;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.http.responses.CloseGatewayResponse;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.http.responses.GetGatewayStatusResponse;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.http.responses.OpenGatewayResponse;
import th.co.loxbit.rest_task_scheduler.gateway.exceptions.InvalidGatewayStatusException;
import th.co.loxbit.rest_task_scheduler.gateway.services.GatewayService;
import th.co.loxbit.rest_task_scheduler.gateway.utilities.GatewayStatus;
import th.co.loxbit.rest_task_scheduler.http.configurers.RequestInterceptorConfigurer;
import th.co.loxbit.rest_task_scheduler.http.configurers.RestServiceConfigurer;
import th.co.loxbit.rest_task_scheduler.http.exceptions.ExternalServiceException;
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
    final int SECTION_CODE = 0;
    try {
      GetGatewayStatusResponse response = restService.get(
        "/status",
        GetGatewayStatusResponse.class
      );
      return GatewayStatus.fromStatus(response.getDesc());
    } catch (RestClientResponseException e) {
      throw ExternalServiceException.builder()
        .serviceCode(SERVICE_CODE)
        .sectionCode(SECTION_CODE)
        .cause(e)
        .build();
    } catch (IllegalArgumentException e) {
      throw InvalidGatewayStatusException.builder()
        .serviceCode(SERVICE_CODE)
        .sectionCode(SECTION_CODE)
        .cause(e)
        .build();
    } catch (RuntimeException e) {
      throw CatchAllException.builder()
        .serviceCode(SERVICE_CODE)
        .sectionCode(SECTION_CODE)
        .cause(e)
        .build();
    }
  }

  @Override
  public void openGateway() {
    final int SECTION_CODE = 100;
    try {
      restService.post(
        "/open",
        null,
        OpenGatewayResponse.class
      );
    } catch (RestClientResponseException e) {
      throw ExternalServiceException.builder()
        .serviceCode(SERVICE_CODE)
        .sectionCode(SECTION_CODE)
        .cause(e)
        .build();
    } catch (RuntimeException e) {
      throw CatchAllException.builder()
        .serviceCode(SERVICE_CODE)
        .sectionCode(SECTION_CODE)
        .cause(e)
        .build();
    } 
  }

  @Override
  public void closeGateway(String maintenanceMsg) {
    final int SECTION_CODE = 200;
    try {
      CloseGatewayRequest requestBody = CloseGatewayRequest.builder()
        .maintenanceMessage(maintenanceMsg)
        .build();
      restService.post(
        "/close",
        requestBody,
        CloseGatewayResponse.class
      );
    } catch (RestClientResponseException e) {
      throw ExternalServiceException.builder()
        .serviceCode(SERVICE_CODE)
        .sectionCode(SECTION_CODE)
        .cause(e)
        .build();
    } catch (RuntimeException e) {
      throw CatchAllException.builder()
        .serviceCode(SERVICE_CODE)
        .sectionCode(SECTION_CODE)
        .cause(e)
        .build();
    }
  }

}
