package dev.hireben.demo.crud_api.common.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.hireben.demo.common_libs.http.resolver.HttpAuthorizationHeaderResolver;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
class HttpWebConfig implements WebMvcConfigurer {

  private final HttpAuthorizationHeaderResolver authHeaderResolver;

  // =============================================================================

  @Override
  public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(authHeaderResolver);
  }

}
