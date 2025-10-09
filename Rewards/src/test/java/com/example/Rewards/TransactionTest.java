package com.example.rewards;

import com.example.rewards.entity.Customer;
import com.example.rewards.entity.RewardPoint;
import com.example.rewards.entity.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
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
                .id(100L)
                .amount(BigDecimal.valueOf(200))
                .spentDetails("Groceries")
                .date(LocalDateTime.of(2025, 8, 25, 10, 0))
                .customer(customer)
                .build();

        assertEquals(100L, transaction.getId());
        assertEquals(BigDecimal.valueOf(200), transaction.getAmount());
        assertEquals("Groceries", transaction.getSpentDetails());
        assertEquals(customer, transaction.getCustomer());
    }

    @Test
    void testTransactionWithRewardPoints() {
        Transaction transaction = Transaction.builder()
                .id(200L)
                .amount(BigDecimal.valueOf(500))
                .spentDetails("Electronics")
                .date(LocalDateTime.of(2025, 8, 25, 12, 30))
                .build();

        RewardPoint rewardPoint = RewardPoint.builder()
                .id(10L)
                .points(120)
                .month("AUGUST")
                .year(2025)
                .transaction(transaction)
                .build();

        transaction.setRewardPoints(Collections.singletonList(rewardPoint));

        assertNotNull(transaction.getRewardPoints());
        assertEquals(1, transaction.getRewardPoints().size());
        assertEquals(120, transaction.getRewardPoints().get(0).getPoints());
        assertEquals(transaction, transaction.getRewardPoints().get(0).getTransaction());
    }

    @Test
    void testTransactionWithMultipleRewardPoints() {
        Transaction transaction = Transaction.builder()
                .id(300L)
                .amount(BigDecimal.valueOf(600))
                .spentDetails("Shopping")
                .date(LocalDateTime.of(2025, 8, 25, 15, 0))
                .build();

        RewardPoint rp1 = RewardPoint.builder().id(11L).points(50).month("AUGUST").year(2025).transaction(transaction).build();
        RewardPoint rp2 = RewardPoint.builder().id(12L).points(70).month("AUGUST").year(2025).transaction(transaction).build();

        transaction.setRewardPoints(Arrays.asList(rp1, rp2));

        assertEquals(2, transaction.getRewardPoints().size());
        assertTrue(transaction.getRewardPoints().contains(rp1));
        assertTrue(transaction.getRewardPoints().contains(rp2));
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();

        Transaction t1 = Transaction.builder()
                .id(1L)
                .amount(BigDecimal.TEN)
                .spentDetails("Books")
                .date(now)
                .build();

        Transaction t2 = Transaction.builder()
                .id(1L)
                .amount(BigDecimal.TEN)
                .spentDetails("Books")
                .date(now)
                .build();

        Transaction t3 = Transaction.builder()
                .id(2L)
                .amount(BigDecimal.TEN)
                .spentDetails("Books")
                .date(now)
                .build();

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
        assertNotEquals(t1, t3);
        assertNotEquals(t1, null);
        assertNotEquals(t1, new Object());
    }

    @Test
    void testToString() {
        Transaction transaction = Transaction.builder()
                .id(400L)
                .amount(BigDecimal.valueOf(100))
                .spentDetails("Utilities")
                .date(LocalDateTime.of(2025, 8, 25, 9, 0))
                .build();

        String str = transaction.toString();
        assertTrue(str.contains("400"));
        assertTrue(str.contains("Utilities"));
        assertTrue(str.contains("100"));
    }

    @Test
    void testNullFields() {
        Transaction transaction = Transaction.builder().build();

        assertNull(transaction.getId());
        assertNull(transaction.getAmount());
        assertNull(transaction.getSpentDetails());
        assertNull(transaction.getDate());
        assertNull(transaction.getCustomer());
        assertNull(transaction.getRewardPoints());
    }
}
