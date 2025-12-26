package dev.hireben.demo.crud_api.common.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.hireben.demo.common_libs.http.resolver.HttpAuthorizationHeaderResolver;
import dev.hireben.demo.common_libs.jwt.JwtClients;
import dev.hireben.demo.common_libs.jwt.api.JwtVerifier;
import dev.hireben.demo.common_libs.reader.KeyReader;

@Configuration
class UtilityConfig {

  @Bean
  JwtVerifier jwtVerifier(
      @Value("${internal.jwt.verify-key-path}") String keyPath)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

    byte[] keyFile = Files.readAllBytes(Path.of(keyPath));
    String keyBase64 = new String(keyFile);
    PublicKey publicKey = KeyReader.readRsaPublicKeyX509(keyBase64);

    return JwtClients.newVerifierWithPublicKey(publicKey);
  }

  // -----------------------------------------------------------------------------

  @Bean
  HttpAuthorizationHeaderResolver httpAuthorizationHeaderResolver(JwtVerifier verifier) {
    return new HttpAuthorizationHeaderResolver(verifier);
  }

}
