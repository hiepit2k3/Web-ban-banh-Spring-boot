package com.tiembanhhoangtube.Repository;

import com.tiembanhhoangtube.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByAccountId (Long accountId);

    Account findByUsername(String username);
}
