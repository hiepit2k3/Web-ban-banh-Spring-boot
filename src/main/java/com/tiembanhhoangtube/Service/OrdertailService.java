package com.tiembanhhoangtube.Service;

import com.tiembanhhoangtube.entity.Orderdetail;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface OrdertailService {
    void flush();

    <S extends Orderdetail> S saveAndFlush(S entity);

    <S extends Orderdetail> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteAllInBatch(Iterable<Orderdetail> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    Orderdetail getReferenceById(Long aLong);

    <S extends Orderdetail> List<S> findAll(Example<S> example);

    <S extends Orderdetail> List<S> findAll(Example<S> example, Sort sort);

    <S extends Orderdetail> List<S> saveAll(Iterable<S> entities);

    List<Orderdetail> findAll();

    List<Orderdetail> findAllById(Iterable<Long> longs);

    <S extends Orderdetail> S save(S entity);

    Optional<Orderdetail> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Orderdetail entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends Orderdetail> entities);

    void deleteAll();

    List<Orderdetail> findAll(Sort sort);

    Page<Orderdetail> findAll(Pageable pageable);

    <S extends Orderdetail> Optional<S> findOne(Example<S> example);

    <S extends Orderdetail> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Orderdetail> long count(Example<S> example);

    <S extends Orderdetail> boolean exists(Example<S> example);

    <S extends Orderdetail, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
