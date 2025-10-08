package com.example.Rewards.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.example.Rewards.Service.RewardService;
import com.example.Rewards.dto.CustomerRewardSummaryDTO;
import com.example.Rewards.dto.MonthlyRewardDTO;
import com.example.Rewards.dto.TotalRewardDTO;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardPointController {

    private final RewardService rewardService;


    @GetMapping("/{customerId}/monthly")
    public List<MonthlyRewardDTO> getMonthlyRewards(@PathVariable Long customerId) {
        return rewardService.getMonthlyRewards(customerId);
    }

    @GetMapping("/{customerId}/total")
    public TotalRewardDTO getTotalRewards(@PathVariable Long customerId) {
        return rewardService.getTotalRewards(customerId);
    }

    @GetMapping("/all")
    public List<CustomerRewardSummaryDTO> getRewardsForAll() {
        return rewardService.getRewardsForAll();
    }
    
    @GetMapping("/{customerId}/customer-rewards")
    public CustomerRewardDTO getCustomerRewardSummary(@PathVariable Long customerId) {
        List<MonthlyRewardDTO> monthlyRewards = rewardService.getMonthlyRewards(customerId);
        int totalPoints = rewardService.getTotalRewards(customerId).getTotalPoints();
        return new CustomerRewardDTO(customerId, monthlyRewards, totalPoints);
    }
}



