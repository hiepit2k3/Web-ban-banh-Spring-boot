package com.tiembanhhoangtube.Service;

import com.tiembanhhoangtube.entity.Order;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface OrderService{
    void flush();

    <S extends Order> S saveAndFlush(S entity);

    <S extends Order> List<S> saveAllAndFlush(Iterable<S> entities);

    @Query("SELECT o,od,p,c FROM Order o JOIN Orderdetail od on o.orderId =  od.orderdetailId JOIN  Product p on od.product.productsId = p.productsId JOIN Account c \n" +
            "on o.account.accountId = c.accountId")
    @Transactional
    List<Object[]> getOrdersWithDetailsAndProducts();

    @Query("select a.fullname,b.amount,b.status,b.orderDate,p.cakeName,p.image,o.quantity from Order  b inner join Account a on b.account.accountId = a.accountId inner join Orderdetail o on b.orderId = o.order.orderId inner join Product p on o.product.productsId = p.productsId")
    @Transactional
    List<Object[]> getOrder();

    @Deprecated
    void deleteInBatch(Iterable<Order> entities);

    void deleteAllInBatch(Iterable<Order> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    @Deprecated
    Order getOne(Long aLong);

    @Deprecated
    Order getById(Long aLong);

    Order getReferenceById(Long aLong);

    <S extends Order> List<S> findAll(Example<S> example);

    <S extends Order> List<S> findAll(Example<S> example, Sort sort);

    <S extends Order> List<S> saveAll(Iterable<S> entities);

    List<Order> findAll();

    List<Order> findAllById(Iterable<Long> longs);

    <S extends Order> S save(S entity);

    Optional<Order> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Order entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends Order> entities);

    void deleteAll();

    List<Order> findAll(Sort sort);

    Page<Order> findAll(Pageable pageable);

    <S extends Order> Optional<S> findOne(Example<S> example);

    <S extends Order> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Order> long count(Example<S> example);

    <S extends Order> boolean exists(Example<S> example);

    <S extends Order, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
