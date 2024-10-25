package th.co.moneydd.adminportal.gateway_scheduler.common.utilities;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import th.co.moneydd.adminportal.gateway_scheduler.gateway.dtos.KongConfig;
import th.co.moneydd.adminportal.gateway_scheduler.gateway.dtos.KongConfigBody;
import th.co.moneydd.adminportal.gateway_scheduler.gateway.dtos.KongConfigBodyData;
import th.co.moneydd.adminportal.gateway_scheduler.gateway.dtos.requests.outbound.KongGatewayRequestOutbound;

@Component
@RequiredArgsConstructor
public class KongRequestUtil {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ObjectMapper objectMapper;

  private final String defaultMsg = "ขออภัย! ไม่สามารถทำรายการได้ \n\nเนื่องจากปิดปรับปรุงระบบชั่วคราว\nเพื่อเพิ่มประสิทธิภาพการใช้งาน\nและการให้บริการที่ดีขึ้น\n\nระหว่าง";

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public KongGatewayRequestOutbound createOpenGatewayRequestBody() throws JsonProcessingException {

    KongConfigBodyData kongConfigBodyData = KongConfigBodyData.builder()
        .appMaintenance(true)
        .appMaintenanceMsg(defaultMsg)
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
        .appMaintenanceMsg(message != null ? message : defaultMsg)
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
        .appMaintenanceMsg(defaultMsg)
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

  // ---------------------------------------------------------------------------//

  public String getDefaultMessage() {
    return defaultMsg;
  }
}
