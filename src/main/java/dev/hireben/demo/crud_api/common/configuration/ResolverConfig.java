package dev.hireben.demo.crud_api.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.hireben.demo.common_libs.annotation.authorization.HttpAuthorizationHeaderResolver;
import dev.hireben.demo.common_libs.utility.jwt.api.JwtVerifier;

@Configuration
class ResolverConfig {

  @Bean
  HttpAuthorizationHeaderResolver httpAuthorizationHeaderResolver(JwtVerifier verifier) {
    return new HttpAuthorizationHeaderResolver(verifier);
  }

}
