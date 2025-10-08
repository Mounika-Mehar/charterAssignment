package com.example.rewards.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerPointsDTO {
    private Long customerId;
    private String month;
    private Integer year;
    private Long totalPoints;

    // Explicit constructor for Hibernate JPQL query
    public CustomerPointsDTO(Long customerId, String month, Integer year, Long totalPointsValue) {
        this.customerId = customerId;
        this.month = month;
        this.year = year;
        this.totalPoints = totalPointsValue;
    }
}


