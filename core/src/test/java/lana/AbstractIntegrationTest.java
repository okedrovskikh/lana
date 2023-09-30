package lana;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(Containers.class)
public class AbstractIntegrationTest {
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", Containers.postgres::getJdbcUrl);
        registry.add("spring.datasource.password", Containers.postgres::getPassword);
        registry.add("spring.datasource.username", Containers.postgres::getUsername);
    }
}
