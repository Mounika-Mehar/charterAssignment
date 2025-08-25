package com.example.Rewards;

import com.example.Rewards.Repository.RewardPointRepository;
import com.example.Rewards.Repository.TransactionRepository;
import com.example.Rewards.Service.RewardServiceImpl;
import com.example.Rewards.dto.TotalRewardDTO;
import com.example.Rewards.entity.RewardPoint;
import com.example.Rewards.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
                .transactionId(1L)
                .amount(BigDecimal.valueOf(120))
                .spentDetails("Electronics")
                .date(LocalDateTime.of(2025, 8, 1, 12, 0))
                .build();

        RewardPoint rewardPoint = RewardPoint.builder()
                .id(1L)
                .points(90)
                .month("AUGUST")
                .year(2025)
                .customerTransaction(transaction)
                .build();

        when(rewardPointRepository.save(any(RewardPoint.class))).thenReturn(rewardPoint);

        RewardPoint result = rewardService.calculateRewardForTransaction(transaction);

        assertEquals(90, result.getPoints());
        assertEquals("AUGUST", result.getMonth());
    }

    @Test
    void testGetMonthlyRewards() {
        when(rewardPointRepository.findMonthlyPointsByCustomer(1L))
                .thenReturn(Arrays.<Object[]>asList(
                        new Object[]{"AUGUST", 2025, 90}
                ));

        var rewards = rewardService.getMonthlyRewards(1L);

        assertEquals(1, rewards.size());
        assertEquals("AUGUST", rewards.get(0).getMonth());
        assertEquals(90, rewards.get(0).getPoints());
    }




    @Test
    void testGetTotalRewards() {
        when(rewardPointRepository.findTotalPointsByCustomer(1L)).thenReturn(150);

        TotalRewardDTO result = rewardService.getTotalRewards(1L);

        assertEquals(150, result.getTotalPoints());
    }
}
