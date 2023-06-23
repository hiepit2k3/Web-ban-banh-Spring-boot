package com.tiembanhhoangtube.Repository;

import com.tiembanhhoangtube.entity.Cartitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartitemRepository extends JpaRepository<Cartitem,Long> {
    @Query("SELECT c FROM Cartitem c WHERE c.product.productsId = :productId")
    Cartitem findByCartitemProductId(Long productId);

    @Transactional
    @Query("SELECT COUNT(c.cartitemId) FROM Cartitem c WHERE c.account.username = :usename")
    int countByCustomerId(String usename);

    @Transactional
    @Query("SELECT c,p FROM Cartitem c join Account a on c.account.accountId = a.accountId JOIN Product p ON c.product.productsId = p.productsId where c.account.accountId = :accountId")
    List<Object[]> getCartitemAndProduct(Long accountId);
}
