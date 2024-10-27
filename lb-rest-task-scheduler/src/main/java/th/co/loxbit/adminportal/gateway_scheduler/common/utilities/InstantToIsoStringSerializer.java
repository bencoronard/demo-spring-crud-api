package th.co.loxbit.adminportal.gateway_scheduler.common.utilities;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class InstantToIsoStringSerializer extends StdSerializer<Instant> {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter
      .ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
      .withZone(ZoneId.of("GMT+7"));

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public InstantToIsoStringSerializer() {
    super(Instant.class);
  }

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeString(FORMATTER.format(value));
  }

}
