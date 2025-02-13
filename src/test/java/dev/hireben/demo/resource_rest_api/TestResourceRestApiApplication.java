package dev.hireben.demo.resource_rest_api;

import org.springframework.boot.SpringApplication;

public class TestResourceRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(ResourceRestApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
