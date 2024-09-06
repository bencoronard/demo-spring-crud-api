package th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.inbound;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetGatewayStatusResponseInbound {

  private final String respCode;
  private final String desc;

}
