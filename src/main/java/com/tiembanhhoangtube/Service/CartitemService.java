package com.tiembanhhoangtube.Service;

import com.tiembanhhoangtube.entity.Cartitem;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartitemService {

    void flush();

    <S extends Cartitem> S saveAndFlush(S entity);

    <S extends Cartitem> List<S> saveAllAndFlush(Iterable<S> entities);


    @Query("SELECT c FROM Cartitem c WHERE c.product.productsId = :productId")
    Cartitem findByCartitemProductId(Long productId);

    @Query("SELECT COUNT(c.cartitemId) FROM Cartitem c WHERE c.account.username = :usename")
    @Transactional
    int countByCustomerId(String usename);

    void deleteAllInBatch(Iterable<Cartitem> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    Cartitem getReferenceById(Long aLong);

    <S extends Cartitem> List<S> findAll(Example<S> example);

    <S extends Cartitem> List<S> findAll(Example<S> example, Sort sort);

    <S extends Cartitem> List<S> saveAll(Iterable<S> entities);

    List<Cartitem> findAll();

    List<Cartitem> findAllById(Iterable<Long> longs);

    <S extends Cartitem> S save(S entity);

    Optional<Cartitem> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Cartitem entity);

    void deleteAll(Iterable<? extends Cartitem> entities);

    void deleteAll();

    List<Cartitem> findAll(Sort sort);

    <S extends Cartitem> long count(Example<S> example);

    <S extends Cartitem> boolean exists(Example<S> example);
}
