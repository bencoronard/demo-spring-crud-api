package dev.hireben.demo.rest.resource;

import org.springframework.boot.SpringApplication;

public class TestResourceApplication {

  public static void main(String[] args) {
    SpringApplication.from(ResourceApplication::main).with(TestcontainersConfiguration.class).run(args);
  }

}
