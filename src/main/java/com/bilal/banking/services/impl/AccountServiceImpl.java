package com.bilal.banking.services.impl;

import com.bilal.banking.dto.AccountDto;
import com.bilal.banking.exceptions.OperationNonPermittedException;
import com.bilal.banking.model.Account;
import com.bilal.banking.repository.AccountRepository;
import com.bilal.banking.services.AccountService;
import com.bilal.banking.validators.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.iban4j.CountryCode;
import org.iban4j.Iban;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    private  final ObjectValidator<AccountDto> validator;
    @Override
    public Integer save(AccountDto dto) {

        /* if (dto.getId() != null){

            throw new OperationNonPermittedException(
                    "Account cannot be updated",
                    "save account",
                    "Account",
                    "Update not permitted"
            );
        }*/

        validator.validate(dto);
        Account account = AccountDto.toEntity(dto);

        if (dto.getId() != null){

            account.setIban(generateRandomIban());
        }

        return repository.save(account).getId();
    }

    @Override
    public List<AccountDto> findAll() {

        return repository.findAll()
                .stream()
                .map(AccountDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto findById(Integer id) {

        return repository.findById(id)
                .map(AccountDto::fromEntity)
                .orElseThrow(() -> new  EntityNotFoundException("No account was found with the ID"+ id));
    }

    @Override
    public void delete(Integer id) {

        repository.deleteById(id);

    }

    private String generateRandomIban(){

        String iban = Iban.random(CountryCode.DE).toFormattedString();

        boolean ibanExists = repository.findByIban(iban).isPresent();

        if(ibanExists){
            generateRandomIban();
        }
        return  iban;
    }
}
