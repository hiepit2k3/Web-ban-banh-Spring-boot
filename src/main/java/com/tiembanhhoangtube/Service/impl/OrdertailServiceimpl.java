package com.tiembanhhoangtube.Service.impl;

import com.tiembanhhoangtube.Repository.OrdertailRepository;
import com.tiembanhhoangtube.Service.OrdertailService;
import com.tiembanhhoangtube.entity.Orderdetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class OrdertailServiceimpl implements OrdertailService {

    @Autowired
    OrdertailRepository  ordertailRepository;

    @Override
    public void flush() {
        ordertailRepository.flush();
    }

    @Override
    public <S extends Orderdetail> S saveAndFlush(S entity) {
        return ordertailRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Orderdetail> List<S> saveAllAndFlush(Iterable<S> entities) {
        return ordertailRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Orderdetail> entities) {
        ordertailRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        ordertailRepository.deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        ordertailRepository.deleteAllInBatch();
    }

    @Override
    public Orderdetail getReferenceById(Long aLong) {
        return ordertailRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends Orderdetail> List<S> findAll(Example<S> example) {
        return ordertailRepository.findAll(example);
    }

    @Override
    public <S extends Orderdetail> List<S> findAll(Example<S> example, Sort sort) {
        return ordertailRepository.findAll(example, sort);
    }

    @Override
    public <S extends Orderdetail> List<S> saveAll(Iterable<S> entities) {
        return ordertailRepository.saveAll(entities);
    }

    @Override
    public List<Orderdetail> findAll() {
        return ordertailRepository.findAll();
    }

    @Override
    public List<Orderdetail> findAllById(Iterable<Long> longs) {
        return ordertailRepository.findAllById(longs);
    }

    @Override
    public <S extends Orderdetail> S save(S entity) {
        return ordertailRepository.save(entity);
    }

    @Override
    public Optional<Orderdetail> findById(Long aLong) {
        return ordertailRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return ordertailRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return ordertailRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        ordertailRepository.deleteById(aLong);
    }

    @Override
    public void delete(Orderdetail entity) {
        ordertailRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        ordertailRepository.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends Orderdetail> entities) {
        ordertailRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        ordertailRepository.deleteAll();
    }

    @Override
    public List<Orderdetail> findAll(Sort sort) {
        return ordertailRepository.findAll(sort);
    }

    @Override
    public Page<Orderdetail> findAll(Pageable pageable) {
        return ordertailRepository.findAll(pageable);
    }

    @Override
    public <S extends Orderdetail> Optional<S> findOne(Example<S> example) {
        return ordertailRepository.findOne(example);
    }

    @Override
    public <S extends Orderdetail> Page<S> findAll(Example<S> example, Pageable pageable) {
        return ordertailRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Orderdetail> long count(Example<S> example) {
        return ordertailRepository.count(example);
    }

    @Override
    public <S extends Orderdetail> boolean exists(Example<S> example) {
        return ordertailRepository.exists(example);
    }

    @Override
    public <S extends Orderdetail, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return ordertailRepository.findBy(example, queryFunction);
    }
}
