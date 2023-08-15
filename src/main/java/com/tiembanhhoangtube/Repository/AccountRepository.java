package com.tiembanhhoangtube.Repository;

import com.tiembanhhoangtube.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByAccountId (Long accountId);
    Optional<Account> findByUsername(String username);
    Account findByEmail(String email);
}
