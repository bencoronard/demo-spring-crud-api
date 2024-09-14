package th.co.loxbit.rest_task_scheduler.gateway.services.implementations;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.common.factories.RetryTemplateFactory;
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

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  @Qualifier("gatewayRestService")
  private final RestService restService;
  private final RetryTemplateFactory retry;

  // ---------------------------------------------------------------------------//
  // Functions
  // ---------------------------------------------------------------------------//

  @Override
  public GatewayStatus getGatewayStatus() {

    GetGatewayStatusResponseInbound response = restService.get(
        "/status",
        GetGatewayStatusResponseInbound.class);

    return GatewayStatus.fromStatus(response.desc());
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void openGateway() {

    restService.postWithRetry(
        "/open",
        null,
        OpenGatewayResponseInbound.class,
        retry.withExponentialBackOff(3, 1000, 2, 3000));
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void closeGateway(String maintenanceMsg) {

    CloseGatewayRequestOutbound requestBody = CloseGatewayRequestOutbound.builder()
        .message(maintenanceMsg)
        .build();

    restService.postWithRetry(
        "/close",
        requestBody,
        CloseGatewayResponseInbound.class,
        retry.withFixedBackOff(3, 1000));

  }

}
