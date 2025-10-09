package com.example.rewards.Service;

import org.springframework.stereotype.Service;

import com.example.rewards.repository.CustomerRepository;
import com.example.rewards.repository.TransactionRepository;
import com.example.rewards.entity.Customer;
import com.example.rewards.entity.Transaction;
import com.example.rewards.exception.ResourceNotFoundException;

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
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
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
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return customer.getTransactions();
    }
}
