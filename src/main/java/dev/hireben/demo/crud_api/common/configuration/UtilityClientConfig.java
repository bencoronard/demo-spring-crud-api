package dev.hireben.demo.crud_api.common.configuration;

import java.security.KeyPair;
import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import dev.hireben.demo.common_libs.factory.JwtClientFactory;
import dev.hireben.demo.common_libs.http.factory.RestClientFactory;
import dev.hireben.demo.common_libs.utility.JwtClient;

@Configuration
class UtilityClientConfig {

  @Bean
  RestClient restClient(
      @Value("${internal.http.client.timeout.connect-in-sec}") Integer connTimeout,
      @Value("${internal.http.client.timeout.read-in-sec}") Integer readTimeout,
      @Value("${internal.http.client.timeout.conn-req-in-sec}") Integer connReqTimeout) {

    return RestClientFactory.buildRestClient(connTimeout, readTimeout, connReqTimeout);
  }

  // -----------------------------------------------------------------------------

  @Bean
  JwtClient jwtClientVerifier(
      @Value("${internal.jwt.verify-key-path}") String keyPath) {

    PublicKey publicKey = null;
    KeyPair keyPair = new KeyPair(publicKey, null);

    return JwtClientFactory.buildJwtClientWithAsymmmetricKeys(null, keyPair);
  }

}
