package sk.balaz.springboot.testing.introduction.product;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgresControllerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static PostgreSQLContainer<?> postgresqlContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:14-alpine"))
                    .withDatabaseName("test")
                    .withUsername("admin")
                    .withPassword("secret");
    static {
        postgresqlContainer.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url", postgresqlContainer.getJdbcUrl(),
                "spring.datasource.username", postgresqlContainer.getUsername(),
                "spring.datasource.password", postgresqlContainer.getPassword()
        ).applyTo(applicationContext);
    }
}
