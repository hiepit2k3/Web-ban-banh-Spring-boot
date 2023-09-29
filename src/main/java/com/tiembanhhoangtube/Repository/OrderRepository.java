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

    @Transactional
    @Query("select a,b,o,p from Order  b inner join Account a on b.account.accountId = a.accountId inner join Orderdetail o on b.orderId = o.order.orderId inner join Product p on o.product.productsId = p.productsId")
    List<Object[]> getOrder();

//    @Query("select * from  orders a inner join accounts a2 on a.account_id = a2.account_id where a.account_id = 55 and a.status = 1")
//    List<Object[]> getDonhangdangdat();
}
