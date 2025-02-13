package dev.hireben.api.rest.abstract_resource;

import org.springframework.boot.SpringApplication;

public class TestAbstractResourceApplication {

	public static void main(String[] args) {
		SpringApplication.from(AbstractResourceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
