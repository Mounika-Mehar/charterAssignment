package com.example.Rewards.Service;

import java.util.List;

import com.example.Rewards.entity.Customer;

public interface CustomerService {
    Customer addCustomer(Customer customer);
    Customer updateCustomer(Long customerId, Customer customer);
    void deleteCustomer(Long customerId);
    Customer getCustomerById(Long customerId);
    List<Customer> getAllCustomers();
}

