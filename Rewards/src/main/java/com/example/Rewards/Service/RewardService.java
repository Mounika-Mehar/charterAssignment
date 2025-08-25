package com.example.Rewards.Service;

import java.util.List;

import com.example.Rewards.dto.CustomerRewardSummaryDTO;
import com.example.Rewards.dto.MonthlyRewardDTO;
import com.example.Rewards.dto.TotalRewardDTO;
import com.example.Rewards.entity.RewardPoint;
import com.example.Rewards.entity.Transaction;

public interface RewardService {
    RewardPoint calculateRewardForTransaction(Transaction transaction);
    List<RewardPoint> calculateRewardsForCustomer(Long customerId);


    List<MonthlyRewardDTO> getMonthlyRewards(Long customerId);
    TotalRewardDTO getTotalRewards(Long customerId);
    List<CustomerRewardSummaryDTO> getRewardsForAll();
}
