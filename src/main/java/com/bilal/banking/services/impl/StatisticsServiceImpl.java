package com.bilal.banking.services.impl;

import com.bilal.banking.dto.TransactionSumDetails;
import com.bilal.banking.model.TransactionType;
import com.bilal.banking.repository.TransactionRepository;
import com.bilal.banking.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final TransactionRepository transactionRepository;
    @Override
    public List<TransactionSumDetails> findSumTransactionsByDate(LocalDate startDate, LocalDate endDate, Integer userId) {
        LocalDateTime  start = LocalDateTime.of(startDate, LocalTime.of(0,0,0));
        LocalDateTime end = LocalDateTime.of(endDate, LocalTime.of(23,59,59));

        return transactionRepository.findSumTransactionsByDate(start, end, userId);
    }

    @Override
    public BigDecimal getAccountBalance(Integer userId) {
        return transactionRepository.findAccountBalance(userId);
    }

    @Override
    public BigDecimal highestTransfert(Integer userId) {
        return transactionRepository.findHighestAmountByTransactionType(userId, TransactionType.TRANSFERT);
    }

    @Override
    public BigDecimal highestDeposit(Integer userId) {
        return transactionRepository.findHighestAmountByTransactionType(userId, TransactionType.DEPOSIT);

    }
}
