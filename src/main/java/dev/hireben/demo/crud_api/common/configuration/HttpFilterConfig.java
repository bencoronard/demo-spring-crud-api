package dev.hireben.demo.crud_api.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.hireben.demo.common_libs.http.filter.HttpApiAccessLogFilter;
import dev.hireben.demo.common_libs.http.filter.HttpApiKeyFilter;

@Configuration
class HttpFilterConfig {

  @Bean
  FilterRegistrationBean<HttpApiAccessLogFilter> apiAccessLogFilter() {
    FilterRegistrationBean<HttpApiAccessLogFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new HttpApiAccessLogFilter());
    registration.setOrder(0);
    registration.addUrlPatterns("/api/*");
    return registration;
  }

  // -----------------------------------------------------------------------------

  @Bean
  FilterRegistrationBean<HttpApiKeyFilter> apiKeyFilter(
      @Value("${internal.api.key}") String apiKey) {
    FilterRegistrationBean<HttpApiKeyFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new HttpApiKeyFilter(apiKey));
    registration.setOrder(1);
    registration.addUrlPatterns("/api/*");
    return registration;
  }

}
