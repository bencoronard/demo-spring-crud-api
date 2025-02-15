package dev.hireben.demo.resource_rest_api.filters.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.hireben.demo.resource_rest_api.filters.HttpHeaderFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FilterConfigs {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  @Value("${info.api.internal.secret-key}")
  private String API_KEY;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Bean
  FilterRegistrationBean<HttpHeaderFilter> httpHeaderFilter() {
    FilterRegistrationBean<HttpHeaderFilter> filter = new FilterRegistrationBean<>();
    filter.setFilter(new HttpHeaderFilter(API_KEY));
    filter.setOrder(0);
    filter.addUrlPatterns("/api/v1/*");
    return filter;
  }

}
