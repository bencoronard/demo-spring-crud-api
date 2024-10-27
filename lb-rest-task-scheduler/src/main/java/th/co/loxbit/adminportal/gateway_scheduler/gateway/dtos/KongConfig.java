package th.co.loxbit.adminportal.gateway_scheduler.gateway.dtos;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Builder;
import lombok.Getter;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.utilities.HttpStatusDeserializer;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.utilities.HttpStatusSerializer;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.utilities.MediaTypeDeserializer;
import th.co.loxbit.adminportal.gateway_scheduler.common.http.utilities.MediaTypeSerializer;

@Builder
@Getter
public class KongConfig {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  @JsonProperty("status_code")
  @JsonSerialize(using = HttpStatusSerializer.class)
  @JsonDeserialize(using = HttpStatusDeserializer.class)
  private final HttpStatus statusCode;

  @JsonProperty("message")
  private final String message;

  @JsonProperty("content_type")
  @JsonSerialize(using = MediaTypeSerializer.class)
  @JsonDeserialize(using = MediaTypeDeserializer.class)
  private final MediaType contentType;

  @JsonProperty("body")
  private final String body;

}
