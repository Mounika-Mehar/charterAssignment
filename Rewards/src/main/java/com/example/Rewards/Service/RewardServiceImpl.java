package com.example.rewards.Service;

import org.springframework.stereotype.Service;

import com.example.rewards.repository.RewardPointRepository;
import com.example.rewards.repository.TransactionRepository;
import com.example.rewards.dto.CustomerRewardSummaryDTO;
import com.example.rewards.dto.MonthlyRewardDTO;
import com.example.rewards.dto.TotalRewardDTO;
import com.example.rewards.entity.RewardPoint;
import com.example.rewards.entity.Transaction;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final RewardPointRepository rewardPointRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public RewardPoint calculateRewardForTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        if (transaction.getAmount() == null) {
            throw new IllegalArgumentException("Transaction amount cannot be null");
        }
        if (transaction.getDate() == null) {
            throw new IllegalArgumentException("Transaction date cannot be null");
        }

        int points = calculatePoints(transaction.getAmount());
        String month = transaction.getDate().getMonth().toString();
        RewardPoint rewardPoint = new RewardPoint(null, points, month, transaction.getDate().getYear(), transaction);
        return rewardPointRepository.save(rewardPoint);
    }


    @Override
    public List<RewardPoint> calculateRewardsForCustomer(Long customerId) {
        List<Transaction> transactions = transactionRepository.findByCustomerId(customerId);
        return transactions.stream()
                .map(this::calculateRewardForTransaction)
                .collect(Collectors.toList());
    }
    @Override
    public List<MonthlyRewardDTO> getMonthlyRewards(Long customerId) {
        return rewardPointRepository.findMonthlyPointsByCustomer(customerId)
                .stream()
                .map(dto -> new MonthlyRewardDTO(
                        dto.getMonth(),        // from MonthlyPointsDTO getter
                        dto.getTotalPoints()   // from MonthlyPointsDTO getter
                ))
                .collect(Collectors.toList());
    }



    @Override
    public TotalRewardDTO getTotalRewards(Long customerId) {
        Integer total = rewardPointRepository.findTotalPointsByCustomer(customerId);
        return new TotalRewardDTO(customerId, total != null ? total : 0);
    }

    @Override
    public List<CustomerRewardSummaryDTO> getRewardsForAll() {
        return rewardPointRepository.findPointsForAllCustomers()
                .stream()
                .map(dto -> new CustomerRewardSummaryDTO(
                        dto.getCustomerId(),
                        dto.getMonth(),
                        dto.getTotalPoints().intValue()
                ))
                .collect(Collectors.toList());
    }



    private int calculatePoints(BigDecimal amount) {
        double amt = amount.doubleValue();
        int points = 0;
        if (amt > 100) {
            points += (int) (2 * (amt - 100));
            points += 50;
        } else if (amt > 50) {
            points += (int) (amt - 50);
        }
        return points;
    }
}
