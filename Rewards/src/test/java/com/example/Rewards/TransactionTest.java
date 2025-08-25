package com.example.Rewards;

import org.junit.jupiter.api.Test;

import com.example.Rewards.entity.Customer;
import com.example.Rewards.entity.RewardPoint;
import com.example.Rewards.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    @Test
    void testTransactionBuilderAndGetters() {
        Customer customer = Customer.builder()
                .id(1L)
                .name("John Doe")
                .email("john@example.com")
                .build();

        Transaction transaction = Transaction.builder()
                .transactionId(100L)
                .amount(BigDecimal.valueOf(200))
                .spentDetails("Groceries")
                .date(LocalDateTime.of(2025, 8, 25, 10, 0))
                .customer(customer)
                .build();

        assertEquals(100L, transaction.getTransactionId());
        assertEquals(BigDecimal.valueOf(200), transaction.getAmount());
        assertEquals("Groceries", transaction.getSpentDetails());
        assertEquals(customer, transaction.getCustomer());
    }

    @Test
    void testTransactionWithRewardPoints() {
        Transaction transaction = Transaction.builder()
                .transactionId(200L)
                .amount(BigDecimal.valueOf(500))
                .spentDetails("Electronics")
                .date(LocalDateTime.of(2025, 8, 25, 12, 30))
                .build();

        RewardPoint rewardPoint = RewardPoint.builder()
                .id(10L)
                .points(120)
                .month("AUGUST")
                .year(2025)
                .customerTransaction(transaction)
                .build();

        transaction.setRewardPoints(Collections.singletonList(rewardPoint));

        assertNotNull(transaction.getRewardPoints());
        assertEquals(1, transaction.getRewardPoints().size());
        assertEquals(120, transaction.getRewardPoints().get(0).getPoints());
        assertEquals(transaction, transaction.getRewardPoints().get(0).getCustomerTransaction());
    }

    @Test
    void testEqualsAndHashCode() {
        Transaction t1 = Transaction.builder()
                .transactionId(1L)
                .amount(BigDecimal.TEN)
                .spentDetails("Books")
                .date(LocalDateTime.now())
                .build();

        Transaction t2 = Transaction.builder()
                .transactionId(1L)
                .amount(BigDecimal.TEN)
                .spentDetails("Books")
                .date(t1.getDate())
                .build();

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }
}
