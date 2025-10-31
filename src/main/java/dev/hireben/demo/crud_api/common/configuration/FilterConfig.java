package dev.hireben.demo.crud_api.common.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.hireben.demo.common_libs.http.filter.ApiAccessLogFilter;
import dev.hireben.demo.common_libs.http.filter.ApiKeyFilter;

@Configuration
class FilterConfig {

  @Bean
  FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter() {
    FilterRegistrationBean<ApiAccessLogFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new ApiAccessLogFilter());
    registration.setOrder(0);
    registration.addUrlPatterns("/api/*");
    return registration;
  }

  // -----------------------------------------------------------------------------

  @Bean
  FilterRegistrationBean<ApiKeyFilter> apiKeyFilter(
      @Value("${internal.api.key}") String apiKey) {
    FilterRegistrationBean<ApiKeyFilter> registration = new FilterRegistrationBean<>();
    registration.setFilter(new ApiKeyFilter(apiKey));
    registration.setOrder(1);
    registration.addUrlPatterns("/api/*");
    return registration;
  }

}
