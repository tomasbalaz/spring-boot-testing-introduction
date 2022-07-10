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
import org.testcontainers.utility.DockerImageName;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    private static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer(DockerImageName.parse("postgres:14-alpine"));

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager entityManager;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username",postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password",postgreSQLContainer::getPassword);

    }

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
    }

    @AfterEach
    void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void shouldGetAllActiveProducts() {
        entityManager.persist( new Product(null, "name1", "deecription1", BigDecimal.ONE, false));
        entityManager.persist( new Product(null, "name2", "deecription2", BigDecimal.ONE, true));

        List<Product> allActiveProducts = productRepository.findAllActiveProducts();

        assertThat(allActiveProducts).hasSize(1);
        assertThat(allActiveProducts.get(0).getName()).isEqualTo("name1");
    }
}