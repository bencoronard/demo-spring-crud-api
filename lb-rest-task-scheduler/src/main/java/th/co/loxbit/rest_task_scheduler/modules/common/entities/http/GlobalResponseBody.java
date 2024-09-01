package th.co.loxbit.rest_task_scheduler.modules.common.entities.http;

import lombok.Data;

@Data
public class GlobalResponseBody<T> {

  private OperationCode operCode;
  private T data;

}
