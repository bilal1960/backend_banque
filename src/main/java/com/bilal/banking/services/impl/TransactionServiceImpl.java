package com.bilal.banking.services.impl;

import com.bilal.banking.dto.TransactionDto;
import com.bilal.banking.repository.TransactionRepository;
import com.bilal.banking.services.TransactionService;
import com.bilal.banking.validators.ObjectValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    private final ObjectValidator<TransactionDto> validator;
    @Override
    public Integer save(TransactionDto dto) {
        return null;
    }

    @Override
    public List<TransactionDto> findAll() {
        return null;
    }

    @Override
    public TransactionDto findById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
