package com.tiembanhhoangtube.Service.impl;

import com.tiembanhhoangtube.Repository.CartitemRepository;
import com.tiembanhhoangtube.Service.CartitemService;
import com.tiembanhhoangtube.entity.Cartitem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class CartitemServiceimpl implements CartitemService {

    @Autowired
    CartitemRepository cartitemRepository;


    @Override
    public void flush() {
        cartitemRepository.flush();
    }

    @Override
    public <S extends Cartitem> S saveAndFlush(S entity) {
        return cartitemRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Cartitem> List<S> saveAllAndFlush(Iterable<S> entities) {
        return cartitemRepository.saveAllAndFlush(entities);
    }

    @Override
    @Query("SELECT c FROM Cartitem c WHERE c.product.productsId = :productId")
    public Cartitem findByCartitemProductId(Long productId) {
        return cartitemRepository.findByCartitemProductId(productId);
    }

    @Override
    @Query("SELECT COUNT(c.cartitemId) FROM Cartitem c WHERE c.account.username = :usename")
    @Transactional
    public int countByCustomerId(String usename) {
        return cartitemRepository.countByCustomerId(usename);
    }

    @Override
    public void deleteAllInBatch(Iterable<Cartitem> entities) {
        cartitemRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        cartitemRepository.deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        cartitemRepository.deleteAllInBatch();
    }

    @Override
    public Cartitem getReferenceById(Long aLong) {
        return cartitemRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends Cartitem> List<S> findAll(Example<S> example) {
        return cartitemRepository.findAll(example);
    }

    @Override
    public <S extends Cartitem> List<S> findAll(Example<S> example, Sort sort) {
        return cartitemRepository.findAll(example, sort);
    }

    @Override
    public <S extends Cartitem> List<S> saveAll(Iterable<S> entities) {
        return cartitemRepository.saveAll(entities);
    }

    @Override
    public List<Cartitem> findAll() {
        return cartitemRepository.findAll();
    }

    @Override
    public List<Cartitem> findAllById(Iterable<Long> longs) {
        return cartitemRepository.findAllById(longs);
    }

    @Override
    public <S extends Cartitem> S save(S entity) {
        return cartitemRepository.save(entity);
    }

    @Override
    public Optional<Cartitem> findById(Long aLong) {
        return cartitemRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return cartitemRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return cartitemRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        cartitemRepository.deleteById(aLong);
    }

    @Override
    public void delete(Cartitem entity) {
        cartitemRepository.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends Cartitem> entities) {
        cartitemRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        cartitemRepository.deleteAll();
    }

    @Override
    public List<Cartitem> findAll(Sort sort) {
        return cartitemRepository.findAll(sort);
    }

    @Override
    public <S extends Cartitem> long count(Example<S> example) {
        return cartitemRepository.count(example);
    }

    @Override
    public <S extends Cartitem> boolean exists(Example<S> example) {
        return cartitemRepository.exists(example);
    }
}
