package th.co.loxbit.adminportal.gateway_scheduler.gateway.utilities;

import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.KongConfig;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.KongConfigBody;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.KongConfigBodyData;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos.requests.outbound.KongGatewayRequestOutbound;

@Component
@RequiredArgsConstructor
public class KongUtil {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ObjectMapper objectMapper;

  @Value("${api.external.gateway.properties.default-plugin-message}")
  private String DEFAULT_MESSAGE;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public KongGatewayRequestOutbound createOpenGatewayRequestBody() throws JsonProcessingException {

    KongConfigBodyData kongConfigBodyData = KongConfigBodyData.builder()
        .appMaintenance(true)
        .appMaintenanceMsg(DEFAULT_MESSAGE)
        .appMaintenanceStart(Instant.ofEpochSecond(0))
        .appMaintenanceEnd(Instant.ofEpochSecond(0))
        .build();

    String kongConfigBody = objectMapper.writeValueAsString(KongConfigBody.builder()
        .respCode(HttpStatus.SERVICE_UNAVAILABLE)
        .respMsg("OK")
        .data(kongConfigBodyData)
        .build());

    KongConfig config = KongConfig.builder()
        .statusCode(HttpStatus.SERVICE_UNAVAILABLE)
        .message(null)
        .contentType(MediaType.APPLICATION_JSON)
        .body(kongConfigBody)
        .build();

    KongGatewayRequestOutbound requestBody = KongGatewayRequestOutbound.builder()
        .config(config)
        .enabled(false)
        .build();

    return requestBody;
  }

  // ---------------------------------------------------------------------------//

  public KongGatewayRequestOutbound createCloseGatewayRequestBody(String message, Instant start, Instant end)
      throws JsonProcessingException {

    KongConfigBodyData kongConfigBodyData = KongConfigBodyData.builder()
        .appMaintenance(true)
        .appMaintenanceMsg(message != null ? message : DEFAULT_MESSAGE)
        .appMaintenanceStart(start)
        .appMaintenanceEnd(end)
        .build();

    String kongConfigBody = objectMapper.writeValueAsString(KongConfigBody.builder()
        .respCode(HttpStatus.SERVICE_UNAVAILABLE)
        .respMsg("OK")
        .data(kongConfigBodyData)
        .build());

    KongConfig config = KongConfig.builder()
        .statusCode(HttpStatus.SERVICE_UNAVAILABLE)
        .message(null)
        .contentType(MediaType.APPLICATION_JSON)
        .body(kongConfigBody)
        .build();

    KongGatewayRequestOutbound requestBody = KongGatewayRequestOutbound.builder()
        .config(config)
        .enabled(true)
        .build();

    return requestBody;
  }

  // ---------------------------------------------------------------------------//

  public KongGatewayRequestOutbound createCloseGatewayOverrideRequestBody(Instant end)
      throws JsonProcessingException {

    KongConfigBodyData kongConfigBodyData = KongConfigBodyData.builder()
        .appMaintenance(true)
        .appMaintenanceMsg(DEFAULT_MESSAGE)
        .appMaintenanceStart(Instant.now())
        .appMaintenanceEnd(end)
        .build();

    String kongConfigBody = objectMapper.writeValueAsString(KongConfigBody.builder()
        .respCode(HttpStatus.SERVICE_UNAVAILABLE)
        .respMsg("OK")
        .data(kongConfigBodyData)
        .build());

    KongConfig config = KongConfig.builder()
        .statusCode(HttpStatus.SERVICE_UNAVAILABLE)
        .message(null)
        .contentType(MediaType.APPLICATION_JSON)
        .body(kongConfigBody)
        .build();

    KongGatewayRequestOutbound requestBody = KongGatewayRequestOutbound.builder()
        .config(config)
        .enabled(true)
        .build();

    return requestBody;
  }

}
