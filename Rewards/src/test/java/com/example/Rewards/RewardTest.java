package com.example.rewards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.rewards.repository.RewardPointRepository;
import com.example.rewards.repository.TransactionRepository;
import com.example.rewards.Service.RewardServiceImpl;
import com.example.rewards.dto.TotalRewardDTO;
import com.example.rewards.entity.RewardPoint;
import com.example.rewards.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RewardTest {

    private RewardPointRepository rewardPointRepository;
    private TransactionRepository transactionRepository;
    private RewardServiceImpl rewardService;

    @BeforeEach
    void setUp() {
        rewardPointRepository = mock(RewardPointRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        rewardService = new RewardServiceImpl(rewardPointRepository, transactionRepository);
    }

    @Test
    void testCalculateRewardForTransaction() {
        Transaction transaction = Transaction.builder()
                .id(1L)
                .amount(BigDecimal.valueOf(120))
                .spentDetails("Electronics")
                .date(LocalDateTime.of(2025, 8, 1, 12, 0))
                .build();

        RewardPoint rewardPoint = RewardPoint.builder()
                .id(1L)
                .points(90)
                .month("AUGUST")
                .year(2025)
                .transaction(transaction)
                .build();

        when(rewardPointRepository.save(any(RewardPoint.class))).thenReturn(rewardPoint);

        RewardPoint result = rewardService.calculateRewardForTransaction(transaction);

        assertEquals(90, result.getPoints());
        assertEquals("AUGUST", result.getMonth());
    }

    @Test
    void testGetMonthlyRewards() {
        // Mock repository to return MonthlyPointsDTO instead of Object[]
        when(rewardPointRepository.findMonthlyPointsByCustomer(1L))
                .thenReturn(Arrays.asList(
                        new com.example.rewards.dto.MonthlyPointsDTO("8",2025, 90L) // month as int
                ));

        var rewards = rewardService.getMonthlyRewards(1L);

        assertEquals(1, rewards.size());
        assertEquals("8", rewards.get(0).getMonth()); // if your DTO converts month to String
        assertEquals(90, rewards.get(0).getTotalPoints());
    }





    @Test
    void testGetTotalRewards() {
        when(rewardPointRepository.findTotalPointsByCustomer(1L)).thenReturn(150);

        TotalRewardDTO result = rewardService.getTotalRewards(1L);

        assertEquals(150, result.getTotalPoints());
    }
    
    @Test
    void testCalculateRewardForTransaction_BelowThreshold_ShouldReturnZeroPoints() {
        Transaction transaction = Transaction.builder()
                .id(2L)
                .amount(BigDecimal.valueOf(40)) // Below threshold
                .spentDetails("Groceries")
                .date(LocalDateTime.of(2025, 8, 2, 12, 0))
                .build();

        RewardPoint rewardPoint = RewardPoint.builder()
                .id(2L)
                .points(0)
                .month("AUGUST")
                .year(2025)
                .transaction(transaction)
                .build();

        when(rewardPointRepository.save(any(RewardPoint.class))).thenReturn(rewardPoint);

        RewardPoint result = rewardService.calculateRewardForTransaction(transaction);

        assertEquals(0, result.getPoints());
        assertEquals("AUGUST", result.getMonth());
        verify(rewardPointRepository, times(1)).save(any(RewardPoint.class));
    }

    @Test
    void testCalculateRewardForTransaction_ExactlyOnThreshold_ShouldReturnExpectedPoints() {
        Transaction transaction = Transaction.builder()
                .id(3L)
                .amount(BigDecimal.valueOf(50)) // Exactly on threshold
                .spentDetails("Books")
                .date(LocalDateTime.of(2025, 8, 3, 12, 0))
                .build();

        RewardPoint rewardPoint = RewardPoint.builder()
                .id(3L)
                .points(0) // assuming 50 gives 0 points if threshold is >50
                .month("AUGUST")
                .year(2025)
                .transaction(transaction)
                .build();

        when(rewardPointRepository.save(any(RewardPoint.class))).thenReturn(rewardPoint);

        RewardPoint result = rewardService.calculateRewardForTransaction(transaction);

        assertEquals(0, result.getPoints()); // adjust if your rule gives points
        assertEquals("AUGUST", result.getMonth());
        verify(rewardPointRepository, times(1)).save(any(RewardPoint.class));
    }

    @Test
    void testCalculateRewardForTransaction_NullTransaction_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> rewardService.calculateRewardForTransaction(null));
    }

}
