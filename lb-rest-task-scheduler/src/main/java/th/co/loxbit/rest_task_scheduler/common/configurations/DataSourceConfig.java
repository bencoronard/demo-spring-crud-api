package th.co.loxbit.rest_task_scheduler.common.configurations;

import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

  // ---------------------------------------------------------------------------//
  // Configurations
  // ---------------------------------------------------------------------------//

  @Bean
  @Primary
  @QuartzDataSource
  @ConfigurationProperties(prefix = "spring.datasource.postgresql")
  DataSource postgreDataSource() {
    return new DriverManagerDataSource();
  }

  // ---------------------------------------------------------------------------//

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.h2")
  DataSource h2DataSource() {
    return new DriverManagerDataSource();
  }

}
