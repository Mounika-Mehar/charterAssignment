package com.example.Rewards.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRewardSummaryDTO {
    private Long customerId;
    private String month;
    private int points;
}


