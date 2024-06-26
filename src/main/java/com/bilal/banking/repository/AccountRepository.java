package com.bilal.banking.repository;

import com.bilal.banking.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {
    Optional<Account> findByIban(String iban);

    Optional<Account> findByUserId(Integer id);
}
