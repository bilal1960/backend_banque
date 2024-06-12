package com.bilal.banking.services.impl;

import com.bilal.banking.dto.AccountDto;
import com.bilal.banking.model.Account;
import com.bilal.banking.repository.AccountRepository;
import com.bilal.banking.services.AccountService;
import com.bilal.banking.validators.ObjectValidator;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    private  final ObjectValidator<AccountDto> validator;
    @Override
    public Integer save(AccountDto dto) {

        validator.validate(dto);
        Account account = AccountDto.toEntity(dto);

        return repository.save(account).getId();
    }

    @Override
    public List<AccountDto> findAll() {
        return null;
    }

    @Override
    public AccountDto findById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
