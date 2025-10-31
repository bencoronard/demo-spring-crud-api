package dev.hireben.demo.crud_api.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import dev.hireben.demo.common_libs.http.factory.RestClientFactory;

@Configuration
class RestClientConfig {

  @Bean
  RestClient restClient(
      @Value("${internal.http.client.timeout.connect-in-sec}") Integer connTimeout,
      @Value("${internal.http.client.timeout.read-in-sec}") Integer readTimeout,
      @Value("${internal.http.client.timeout.conn-req-in-sec}") Integer connReqTimeout) {

    return RestClientFactory.buildRestClient(connTimeout, readTimeout, connReqTimeout);
  }

}
