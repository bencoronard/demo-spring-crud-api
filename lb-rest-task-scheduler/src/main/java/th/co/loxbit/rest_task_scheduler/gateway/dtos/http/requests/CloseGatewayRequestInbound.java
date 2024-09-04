package th.co.loxbit.rest_task_scheduler.gateway.dtos.http.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CloseGatewayRequestInbound {

  @NotBlank
  private final String maintenanceMessage;

}
