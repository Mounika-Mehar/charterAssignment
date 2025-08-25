package com.example.Rewards.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RewardPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int points;

    @Column(name="reward_month")
    private String month;
    
    @Column(name="reward_year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction customerTransaction;
}

