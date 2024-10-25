package th.co.moneydd.adminportal.gateway_scheduler.common.http.utilities;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class HttpStatusSerializer extends StdSerializer<HttpStatus> {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public HttpStatusSerializer() {
    super(HttpStatus.class);
  }

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public void serialize(HttpStatus value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeNumber(value.value());
  }

}
