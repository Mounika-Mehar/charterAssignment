package com.example.Rewards.Service;

import org.springframework.stereotype.Service;

import com.example.Rewards.Repository.RewardPointRepository;
import com.example.Rewards.Repository.TransactionRepository;
import com.example.Rewards.dto.CustomerRewardSummaryDTO;
import com.example.Rewards.dto.MonthlyRewardDTO;
import com.example.Rewards.dto.TotalRewardDTO;
import com.example.Rewards.entity.RewardPoint;
import com.example.Rewards.entity.Transaction;

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
                .map(obj -> new MonthlyRewardDTO(
                        (String) obj[0],               // month
                        ((Number) obj[2]).intValue()   //points
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
                .map(obj -> new CustomerRewardSummaryDTO(
                        ((Number) obj[0]).longValue(),
                        (String) obj[1],
                        ((Number) obj[2]).intValue()
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
