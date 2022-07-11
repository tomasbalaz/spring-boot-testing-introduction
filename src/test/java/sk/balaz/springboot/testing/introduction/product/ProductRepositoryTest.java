package sk.balaz.springboot.testing.introduction.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class ProductRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgresqlContainer =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:14-alpine"))
                    .withDatabaseName("test")
                    .withUsername("admin")
                    .withPassword("secret");

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager entityManager;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

//    @BeforeAll
//    static void beforeAll() {
//        postgresqlContainer.start();
//    }
//
//    @AfterEach
//    void tearDown() {
//        postgresqlContainer.stop();
//    }

    @Test
    void shouldGetAllActiveProducts() {
        entityManager.persist( new Product(null, "name1", "deecription1", BigDecimal.ONE, false));
        entityManager.persist( new Product(null, "name2", "deecription2", BigDecimal.ONE, true));

        List<Product> allActiveProducts = productRepository.findAllActiveProducts();

        assertThat(allActiveProducts).hasSize(1);
        assertThat(allActiveProducts.get(0).getName()).isEqualTo("name1");
    }
}