package com.tiembanhhoangtube.Repository;

import com.tiembanhhoangtube.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT p FROM Product p WHERE p.productsId = :productId")
    Product getProductById(Long productId);
}
