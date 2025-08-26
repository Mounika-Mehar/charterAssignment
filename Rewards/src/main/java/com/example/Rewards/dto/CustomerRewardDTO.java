package com.example.rewards.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRewardDTO {
    private Long customerId;
    private List<MonthlyRewardDTO> monthlyRewards; 
    private int totalPoints;
}
