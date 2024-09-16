package th.co.loxbit.rest_task_scheduler.common.security.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import th.co.loxbit.rest_task_scheduler.common.security.filters.ApiKeyFilter;
import th.co.loxbit.rest_task_scheduler.common.security.filters.UserIdFilter;

@Configuration
public class SecurityConfig {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  @Value("${api.internal.secret.key}")
  private String apiKey;

  // ---------------------------------------------------------------------------//
  // Configurations
  // ---------------------------------------------------------------------------//

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.cors(
        cors -> cors.disable());

    http.csrf(
        csrf -> csrf.disable());

    http.authorizeHttpRequests(
        authorize -> authorize.anyRequest().permitAll());

    http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

    http.addFilterBefore(apiKeyFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(userIdFilter(), ApiKeyFilter.class);

    return http.build();
  }

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Bean
  ApiKeyFilter apiKeyFilter() {
    return new ApiKeyFilter(apiKey);
  }

  // ---------------------------------------------------------------------------//

  @Bean
  UserIdFilter userIdFilter() {
    return new UserIdFilter();
  }

}
