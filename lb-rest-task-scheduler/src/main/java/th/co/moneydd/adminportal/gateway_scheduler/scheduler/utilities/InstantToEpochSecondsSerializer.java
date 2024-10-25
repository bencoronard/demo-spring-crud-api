package th.co.moneydd.adminportal.gateway_scheduler.scheduler.utilities;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class InstantToEpochSecondsSerializer extends StdSerializer<Instant> {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public InstantToEpochSecondsSerializer() {
    super(Instant.class);
  }

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeNumber(value.getEpochSecond());
  }

}
