package th.co.loxbit.adminportal.gateway_scheduler.common.http.utilities;

import java.io.IOException;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class MediaTypeSerializer extends StdSerializer<MediaType> {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public MediaTypeSerializer() {
    super(MediaType.class);
  }

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public void serialize(MediaType value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeString(value.getType() + "/" + value.getSubtype());
  }

}
