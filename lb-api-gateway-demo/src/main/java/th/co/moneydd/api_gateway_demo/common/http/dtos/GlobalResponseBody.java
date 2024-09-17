package th.co.moneydd.api_gateway_demo.common.http.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GlobalResponseBody {

  private final int respCode;
  private final String desc;

}
