package th.co.loxbit.rest_task_scheduler.modules.gateway.entities.http.responses;

import lombok.Data;

@Data
public class GetGatewayStatusResponse {

  private String respCode;
  private String desc;

}
