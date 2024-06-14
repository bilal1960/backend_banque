package com.bilal.banking.repository;

import com.bilal.banking.dto.ContactDto;
import com.bilal.banking.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Integer> {
    List<Contact> findAllByUserId(Integer userId);
}
