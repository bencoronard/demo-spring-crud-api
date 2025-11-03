package dev.hireben.demo.crud_api.common.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.hireben.demo.common_libs.annotation.authorization.HttpAuthorizationHeaderResolver;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
class HttpWebConfig implements WebMvcConfigurer {

  private final HttpAuthorizationHeaderResolver HTTP_AUTH_HEADER_RESOLVER;

  // =============================================================================

  @Override
  public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(HTTP_AUTH_HEADER_RESOLVER);
  }

}
