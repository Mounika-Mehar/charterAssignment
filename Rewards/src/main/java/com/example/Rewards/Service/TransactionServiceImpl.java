package com.example.Rewards.Service;

import org.springframework.stereotype.Service;

import com.example.Rewards.Repository.CustomerRepository;
import com.example.Rewards.Repository.TransactionRepository;
import com.example.Rewards.entity.Customer;
import com.example.Rewards.entity.Transaction;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, CustomerRepository customerRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Transaction addTransaction(Long customerId, Transaction transaction) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        transaction.setCustomer(customer);
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    @Override
    public List<Transaction> getTransactionsByCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return customer.getTransactions();
    }
}
