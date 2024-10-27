package th.co.loxbit.adminportal.gateway_scheduler.common.http.utilities;

import java.io.IOException;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class MediaTypeDeserializer extends StdDeserializer<MediaType> {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public MediaTypeDeserializer() {
    super(MediaType.class);
  }

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public MediaType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    String contentType = p.getText();
    return MediaType.valueOf(contentType);
  }

}
