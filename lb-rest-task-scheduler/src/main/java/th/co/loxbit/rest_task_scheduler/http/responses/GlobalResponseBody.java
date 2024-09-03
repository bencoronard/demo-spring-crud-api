package th.co.loxbit.rest_task_scheduler.http.responses;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GlobalResponseBody<T> {

  private String exitCode;

  private String desc;

  private T payload;

}
