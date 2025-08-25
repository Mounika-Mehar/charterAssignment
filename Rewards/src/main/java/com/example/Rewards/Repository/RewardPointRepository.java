package com.example.Rewards.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Rewards.entity.RewardPoint;

import java.util.List;

@Repository
public interface RewardPointRepository extends JpaRepository<RewardPoint, Long> {

    @Query("SELECT r.customerTransaction.customer.id, r.month, r.year, SUM(r.points) " +
           "FROM RewardPoint r " +
           "GROUP BY r.customerTransaction.customer.id, r.month, r.year")
    List<Object[]> findPointsForAllCustomers();

    @Query("SELECT r.month, r.year, SUM(r.points) " +
           "FROM RewardPoint r " +
           "WHERE r.customerTransaction.customer.id = :customerId " +
           "GROUP BY r.month, r.year")
    List<Object[]> findMonthlyPointsByCustomer(@Param("customerId") Long customerId);

    @Query("SELECT SUM(r.points) " +
           "FROM RewardPoint r " +
           "WHERE r.customerTransaction.customer.id = :customerId")
    Integer findTotalPointsByCustomer(@Param("customerId") Long customerId);
}


