package com.tiembanhhoangtube.Service;


import com.tiembanhhoangtube.entity.Account;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface AccountService {
    Account findByAccountId(Long accountId);

    Account login(String username, String password);


    Account findByEmail(String email);

    Optional<Account> findByUsername(String username);

    void flush();

    <S extends Account> S saveAndFlush(S entity);

    <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities);

    void deleteAllInBatch(Iterable<Account> entities);

    void deleteAllByIdInBatch(Iterable<Long> longs);

    void deleteAllInBatch();

    Account getReferenceById(Long aLong);

    <S extends Account> List<S> findAll(Example<S> example);

    <S extends Account> List<S> findAll(Example<S> example, Sort sort);

    <S extends Account> List<S> saveAll(Iterable<S> entities);

    List<Account> findAll();

    List<Account> findAllById(Iterable<Long> longs);

    <S extends Account> S save(S entity);

    Optional<Account> findById(Long aLong);

    boolean existsById(Long aLong);

    long count();

    void deleteById(Long aLong);

    void delete(Account entity);

    void deleteAllById(Iterable<? extends Long> longs);

    void deleteAll(Iterable<? extends Account> entities);

    void deleteAll();

    List<Account> findAll(Sort sort);

    Page<Account> findAll(Pageable pageable);

    <S extends Account> Optional<S> findOne(Example<S> example);

    <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable);

    <S extends Account> long count(Example<S> example);

    <S extends Account> boolean exists(Example<S> example);

    <S extends Account, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction);
}
