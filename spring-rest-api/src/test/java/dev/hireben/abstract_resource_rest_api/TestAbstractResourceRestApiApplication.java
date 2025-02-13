package dev.hireben.abstract_resource_rest_api;

import org.springframework.boot.SpringApplication;

public class TestAbstractResourceRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.from(AbstractResourceRestApiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
