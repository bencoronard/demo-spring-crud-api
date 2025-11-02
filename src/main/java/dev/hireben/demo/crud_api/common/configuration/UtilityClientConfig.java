package dev.hireben.demo.crud_api.common.configuration;

import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import dev.hireben.demo.common_libs.utility.http.RestClients;
import dev.hireben.demo.common_libs.utility.jwt.JwtClients;
import dev.hireben.demo.common_libs.utility.jwt.api.JwtVerifier;

@Configuration
class UtilityClientConfig {

  @Bean
  RestClient restClient(
      @Value("${internal.http.client.timeout.connect-in-sec}") Integer connTimeout,
      @Value("${internal.http.client.timeout.read-in-sec}") Integer readTimeout,
      @Value("${internal.http.client.timeout.conn-req-in-sec}") Integer connReqTimeout) {

    return RestClients.newClient(connTimeout, readTimeout, connReqTimeout);
  }

  // -----------------------------------------------------------------------------

  @Bean
  JwtVerifier jwtVerifier(
      @Value("${internal.jwt.verify-key-path}") String keyPath) {

    PublicKey publicKey = null;

    return JwtClients.newVerifierWithPublicKey(publicKey);
  }

}
