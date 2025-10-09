package com.example.rewards.Service;

import org.springframework.stereotype.Service;

import com.example.rewards.repository.CustomerRepository;
import com.example.rewards.entity.Customer;
import com.example.rewards.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
        customerRepository.delete(customer);
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
    }

    @Override
    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    customer.setName(updatedCustomer.getName());
                    customer.setEmail(updatedCustomer.getEmail());
                    return customerRepository.save(customer);
                }).orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + customerId));
    }


    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}

