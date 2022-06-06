package sk.balaz.springboot.testing.introduction.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void shouldGetAllActiveProducts() {
        entityManager.persist( new Product(null, "name1", "deecription1", BigDecimal.ONE, false));
        entityManager.persist( new Product(null, "name2", "deecription2", BigDecimal.ONE, true));

        List<Product> allActiveProducts = productRepository.findAllActiveProducts();

        assertThat(allActiveProducts).hasSize(1);
        assertThat(allActiveProducts.get(0).getName()).isEqualTo("name1");
    }
}