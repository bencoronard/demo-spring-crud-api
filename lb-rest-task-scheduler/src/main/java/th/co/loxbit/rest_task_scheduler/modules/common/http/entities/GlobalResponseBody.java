package th.co.loxbit.rest_task_scheduler.modules.common.http.entities;

import lombok.Data;

@Data
public class GlobalResponseBody<T> {

  private OperationCode operCode;
  private String desc;
  private T data;

}
