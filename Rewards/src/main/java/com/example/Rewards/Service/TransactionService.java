package com.example.Rewards.Service;

import java.util.List;

import com.example.Rewards.entity.Transaction;

public interface TransactionService {
    Transaction addTransaction(Long customerId, Transaction transaction);
    void deleteTransaction(Long transactionId);
    List<Transaction> getTransactionsByCustomer(Long customerId);
}