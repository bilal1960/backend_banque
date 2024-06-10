package com.bilal.banking.model.repository;

import com.bilal.banking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
