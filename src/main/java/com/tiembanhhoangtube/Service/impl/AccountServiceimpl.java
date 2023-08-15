package com.tiembanhhoangtube.Service.impl;


import com.tiembanhhoangtube.Repository.AccountRepository;
import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


@Service
public class AccountServiceimpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Account findByAccountId(Long accountId) {
        return accountRepository.findByAccountId(accountId);
    }

    @Override
    public Account login(String username, String password){
//        Account account = findByUsername(username);
//        if (account != null) {
//            if (bCryptPasswordEncoder.matches(password, account.getPassword())) {
//                System.out.println("Mật khẩu trùng khớp");
//                return account;
//            } else {
//                System.out.println("Mật khẩu không trùng khớp");
//            }
//        } else {
//            System.out.println("Tên đăng nhập không tồn tại");
//        }
        return null;
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public void flush() {
        accountRepository.flush();
    }

    @Override
    public <S extends Account> S saveAndFlush(S entity) {
        return accountRepository.saveAndFlush(entity);
    }

    @Override
    public <S extends Account> List<S> saveAllAndFlush(Iterable<S> entities) {
        return accountRepository.saveAllAndFlush(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Account> entities) {
        accountRepository.deleteAllInBatch(entities);
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        accountRepository.deleteAllByIdInBatch(longs);
    }

    @Override
    public void deleteAllInBatch() {
        accountRepository.deleteAllInBatch();
    }

    @Override
    public Account getReferenceById(Long aLong) {
        return accountRepository.getReferenceById(aLong);
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example) {
        return accountRepository.findAll(example);
    }

    @Override
    public <S extends Account> List<S> findAll(Example<S> example, Sort sort) {
        return accountRepository.findAll(example, sort);
    }

    @Override
    public <S extends Account> List<S> saveAll(Iterable<S> entities) {
        return accountRepository.saveAll(entities);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public List<Account> findAllById(Iterable<Long> longs) {
        return accountRepository.findAllById(longs);
    }

    @Override
    public <S extends Account> S save(S entity) {
        Account account = findByEmail(entity.getEmail());
        if(account != null){
            if(entity.getPassword().equals("") || entity.getPassword().equals(account.getPassword())){
                System.out.println("mật khẩu cũ:"+account.getPassword());
                entity.setPassword(account.getPassword());
                if(entity.getImage() == null){
                    entity.setImage(account.getImage());
                }
                return accountRepository.save(entity);
            }
            else if(entity.getImage() == null){
                entity.setImage(account.getImage());
            }
            entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
            return accountRepository.save(entity);
        }
        entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        return accountRepository.save(entity);
    }

    public Account AuthenGoogle(Account entity){
        Account account = findByEmail(entity.getEmail());
        if(account != null){
            return account;
        }
        entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        return save(entity);
    }

//    public Account UpdateInformation(Account entity){
//
//    }

    @Override
    public Optional<Account> findById(Long aLong) {
        return accountRepository.findById(aLong);
    }

    @Override
    public boolean existsById(Long aLong) {
        return accountRepository.existsById(aLong);
    }

    @Override
    public long count() {
        return accountRepository.count();
    }

    @Override
    public void deleteById(Long aLong) {
        accountRepository.deleteById(aLong);
    }

    @Override
    public void delete(Account entity) {
        accountRepository.delete(entity);
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        accountRepository.deleteAllById(longs);
    }

    @Override
    public void deleteAll(Iterable<? extends Account> entities) {
        accountRepository.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        accountRepository.deleteAll();
    }

    @Override
    public List<Account> findAll(Sort sort) {
        return accountRepository.findAll(sort);
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public <S extends Account> Optional<S> findOne(Example<S> example) {
        return accountRepository.findOne(example);
    }

    @Override
    public <S extends Account> Page<S> findAll(Example<S> example, Pageable pageable) {
        return accountRepository.findAll(example, pageable);
    }

    @Override
    public <S extends Account> long count(Example<S> example) {
        return accountRepository.count(example);
    }

    @Override
    public <S extends Account> boolean exists(Example<S> example) {
        return accountRepository.exists(example);
    }

    @Override
    public <S extends Account, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return accountRepository.findBy(example, queryFunction);
    }
}
