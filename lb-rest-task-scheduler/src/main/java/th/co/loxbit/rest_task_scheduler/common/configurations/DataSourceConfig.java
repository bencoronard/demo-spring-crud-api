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

  @Bean
  @Primary
  @ConfigurationProperties("spring.datasource")
  DataSource postgreDataSource() {
    return new DriverManagerDataSource();
  }

  @Bean
  @QuartzDataSource
  @ConfigurationProperties("quartz-data-source")
  DataSource h2DataSource() {
    return new DriverManagerDataSource();
  }

}
