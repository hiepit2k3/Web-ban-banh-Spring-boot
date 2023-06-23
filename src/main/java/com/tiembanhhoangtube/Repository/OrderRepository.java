package com.tiembanhhoangtube.Repository;

import com.tiembanhhoangtube.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Transactional
    @Query("SELECT o,od,p,c FROM Order o JOIN Orderdetail od on o.orderId =  od.orderdetailId JOIN  Product p on od.product.productsId = p.productsId JOIN Account c \n" +
            "on o.account.accountId = c.accountId")
    List<Object[]> getOrdersWithDetailsAndProducts();
}
