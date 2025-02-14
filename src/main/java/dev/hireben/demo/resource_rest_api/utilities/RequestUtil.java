package dev.hireben.demo.resource_rest_api.utilities;

import java.util.Optional;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestUtil {

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public <T> Optional<T> getAttribute(WebRequest request, String key, Class<T> type) {
    Object attr = request.getAttribute(key, RequestAttributes.SCOPE_REQUEST);

    if (attr != null && type.isInstance(attr)) {
      return Optional.of(type.cast(attr));
    }

    return Optional.empty();
  }

}
