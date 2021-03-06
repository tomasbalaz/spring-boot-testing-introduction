package sk.balaz.springboot.testing.introduction.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.disabled = false ")
    List<Product> findAllActiveProducts();
}
