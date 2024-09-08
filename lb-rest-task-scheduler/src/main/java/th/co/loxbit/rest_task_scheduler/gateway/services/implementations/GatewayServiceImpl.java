package th.co.loxbit.rest_task_scheduler.gateway.services.implementations;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.common.exceptions.CatchAllServiceException;
import th.co.loxbit.rest_task_scheduler.common.factories.RetryTemplateFactory;
import th.co.loxbit.rest_task_scheduler.common.http.exceptions.Resp4xxException;
import th.co.loxbit.rest_task_scheduler.common.http.services.RestService;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.requests.outbound.CloseGatewayRequestOutbound;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.inbound.CloseGatewayResponseInbound;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.inbound.GetGatewayStatusResponseInbound;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.inbound.OpenGatewayResponseInbound;
import th.co.loxbit.rest_task_scheduler.gateway.entities.GatewayStatus;
import th.co.loxbit.rest_task_scheduler.gateway.services.GatewayService;

@Service
@RequiredArgsConstructor
public class GatewayServiceImpl implements GatewayService {

  @Qualifier("gatewayRestService")
  private final RestService restService;
  private final RetryTemplateFactory retry;

  @Override
  public GatewayStatus getGatewayStatus() {

    final int SECTION_CODE = 0;

    try {

      GetGatewayStatusResponseInbound response = restService.get(
          "/status",
          GetGatewayStatusResponseInbound.class);

      return GatewayStatus.fromStatus(response.desc());

    } catch (RestClientResponseException e) {

      throw Resp4xxException.builder()
          .serviceCode(SERVICE_CODE)
          .build();

    } catch (RuntimeException e) {

      throw CatchAllServiceException.builder()
          .serviceCode(SERVICE_CODE)
          .sectionCode(SECTION_CODE)
          .build();
    }
  }

  @Override
  public void openGateway() {

    final int SECTION_CODE = 100;

    try {

      restService.postWithRetry(
          "/open",
          null,
          OpenGatewayResponseInbound.class,
          retry.withExponentialBackOff(3, 1000, 2, 3000));

    } catch (RuntimeException e) {

      throw CatchAllServiceException.builder()
          .serviceCode(SERVICE_CODE)
          .sectionCode(SECTION_CODE)
          .build();

    }
  }

  @Override
  public void closeGateway(String maintenanceMsg) {

    final int SECTION_CODE = 200;

    try {

      CloseGatewayRequestOutbound requestBody = CloseGatewayRequestOutbound.builder()
          .message(maintenanceMsg)
          .build();

      restService.postWithRetry(
          "/close",
          requestBody,
          CloseGatewayResponseInbound.class,
          retry.withFixedBackOff(3, 1000));

    } catch (RuntimeException e) {

      throw CatchAllServiceException.builder()
          .serviceCode(SERVICE_CODE)
          .sectionCode(SECTION_CODE)
          .build();

    }
  }

}
