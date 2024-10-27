package th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Getter;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.utilities.HttpStatusDeserializer;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.utilities.HttpStatusSerializer;

@Builder
@Getter
public class KongConfigBody {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  @JsonProperty("respCode")
  @JsonSerialize(using = HttpStatusSerializer.class)
  @JsonDeserialize(using = HttpStatusDeserializer.class)
  private final HttpStatus respCode;

  @JsonProperty("respMsg")
  private final String respMsg;

  @JsonProperty("data")
  private final KongConfigBodyData data;

}
