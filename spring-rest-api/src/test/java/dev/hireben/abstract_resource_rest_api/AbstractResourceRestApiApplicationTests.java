package dev.hireben.abstract_resource_rest_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class AbstractResourceRestApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
