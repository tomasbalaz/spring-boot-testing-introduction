package sk.balaz.springboot.testing.introduction.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductServiceTest {

    private ProductService productService;

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void shouldReturnOnlyActiveProducts() {
        //given - arrange
        Product p1 = new Product(1L, "name1", "deecription1", BigDecimal.ONE, false);
        Product p2 = new Product(2L, "name2", "deecription2", BigDecimal.ONE, true);

        BDDMockito.given(productRepository.findAll())
                .willReturn(List.of(p1, p2));

        //when - act
        List<Product> activeProducts = productService.getAllProducts();

        //then - assert
        assertThat(activeProducts).hasSize(1);
        assertThat(activeProducts.get(0).getId()).isEqualTo(1L);
    }
}