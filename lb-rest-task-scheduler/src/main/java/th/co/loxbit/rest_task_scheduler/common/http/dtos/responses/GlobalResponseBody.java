package th.co.loxbit.rest_task_scheduler.common.http.dtos.responses;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GlobalResponseBody<T> {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final int respCode;
  private final String desc;
  private final T payload;

}
