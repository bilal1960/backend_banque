package com.bilal.banking.services.impl;

import com.bilal.banking.dto.TransactionDto;
import com.bilal.banking.model.Transaction;
import com.bilal.banking.model.TransactionType;
import com.bilal.banking.repository.TransactionRepository;
import com.bilal.banking.services.TransactionService;
import com.bilal.banking.validators.ObjectValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    private final ObjectValidator<TransactionDto> validator;
    @Override
    public Integer save(TransactionDto dto) {

        validator.validate(dto);

        Transaction transaction = TransactionDto.toEntity(dto);
        BigDecimal transactionMultiplier = BigDecimal.valueOf(getTransactionMultiplier(transaction.getType()));
        BigDecimal amount = transaction.getAmount().multiply(transactionMultiplier);
        transaction.setAmount(amount);
        return repository.save(transaction).getId();
    }

    @Override
    public List<TransactionDto> findAll() {

        return repository.findAll()
                .stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto findById(Integer id) {

        return repository.findById(id)
                .map(TransactionDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("No transaction was found with the  ID : " + id));
    }

    @Override
    public void delete(Integer id) {

        repository.deleteById(id);

    }

    private int getTransactionMultiplier(TransactionType type){

        return TransactionType.TRANSFERT == type ? -1 :  1;
    }

    @Override
    public List<TransactionDto> findAllByUserId(Integer userId) {

        return repository.findAllByUserId(userId)
                .stream()
                .map(TransactionDto::fromEntity)
                .collect(Collectors.toList());
    }
}
