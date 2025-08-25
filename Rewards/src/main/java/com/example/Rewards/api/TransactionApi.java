package com.example.Rewards.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.Rewards.Service.TransactionService;
import com.example.Rewards.entity.Transaction;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionApi {

    private final TransactionService transactionService;

    @PostMapping("/{customerId}")
    public Transaction addTransaction(@PathVariable Long customerId,
                                      @RequestBody Transaction transaction) {
        return transactionService.addTransaction(customerId, transaction);
    }

    @GetMapping("/customer/{customerId}")
    public List<Transaction> getTransactionsByCustomer(@PathVariable Long customerId) {
        return transactionService.getTransactionsByCustomer(customerId);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}


