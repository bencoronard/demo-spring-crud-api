package th.co.loxbit.rest_task_scheduler.security.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.cors(
        cors -> cors.disable());

    http.csrf(
        csrf -> csrf.disable());

    http.authorizeHttpRequests(
        authorize -> authorize.anyRequest().permitAll());

    return http.build();
  }

}
