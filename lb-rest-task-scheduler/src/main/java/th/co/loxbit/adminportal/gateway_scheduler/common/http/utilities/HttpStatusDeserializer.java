package th.co.loxbit.adminportal.gateway_scheduler.common.http.utilities;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class HttpStatusDeserializer extends StdDeserializer<HttpStatus> {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public HttpStatusDeserializer() {
    super(HttpStatus.class);
  }

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public HttpStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    String statusCode = p.getText();
    return HttpStatus.valueOf(Integer.parseInt(statusCode));
  }

}
