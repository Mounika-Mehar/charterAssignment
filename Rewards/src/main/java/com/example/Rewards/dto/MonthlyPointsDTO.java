package com.example.rewards.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class MonthlyPointsDTO {
    private String month;
    private int year;
    private Long totalPoints; // SUM(...) returns Long

    public MonthlyPointsDTO(String month, int year, Long totalPoints) {
        this.month = month;
        this.year=year;
        this.totalPoints = totalPoints;
    }
}

