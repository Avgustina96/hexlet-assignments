package exercise.repository;

import exercise.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAll();

    @Query("SELECT p FROM Product p WHERE (:priceMin IS NULL OR p.price > :priceMin) AND (:priceMax IS NULL OR p.price < :priceMax) order by price")
    List<Product> findAllByPriceBetween(@Param("priceMin") Double priceMin, @Param("priceMax") Double priceMax);

}
