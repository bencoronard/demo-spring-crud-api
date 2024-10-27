package th.co.loxbit.adminportal.gateway_scheduler.common.http.configurations;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@Configuration
public class HttpConfig {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  @Value("${api.external.timeout-in-sec}")
  private int timeoutInSeconds;

  // ---------------------------------------------------------------------------//
  // Configurations
  // ---------------------------------------------------------------------------//

  @Bean
  ClientHttpRequestFactory clientHttpRequestFactory() {

    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(50);
    connectionManager.setDefaultMaxPerRoute(20);

    CloseableHttpClient httpClient = HttpClients.custom()
        .setConnectionManager(connectionManager)
        .build();

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
    factory.setConnectTimeout(timeoutInSeconds);

    return factory;
  }

}
