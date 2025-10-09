package com.example.rewards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.example.rewards.repository.CustomerRepository;
import com.example.rewards.Service.CustomerServiceImpl;
import com.example.rewards.entity.Customer;
import com.example.rewards.exception.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerTest {

    private CustomerRepository customerRepository;
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customerRepository = Mockito.mock(CustomerRepository.class);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void testAddCustomer() {
        Customer customer = new Customer(1L, "John", "john@example.com", null);
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer saved = customerService.addCustomer(customer);

        assertEquals("John", saved.getName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testUpdateCustomer() {
        Customer existing = new Customer(1L, "John", "john@example.com", null);
        Customer updated = new Customer(1L, "Johnny", "johnny@example.com", null);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(customerRepository.save(existing)).thenReturn(existing);

        Customer result = customerService.updateCustomer(1L, updated);

        assertEquals("Johnny", result.getName());
        assertEquals("johnny@example.com", result.getEmail());
    }

    @Test
    void testGetCustomerById_NotFound() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(1L));
    }

    @Test
    void testGetAllCustomers() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(new Customer(), new Customer()));
        assertEquals(2, customerService.getAllCustomers().size());
    }
    
    @Test
    void givenExistingCustomerId_whenDeleteCustomer_thenCustomerIsDeleted() {
        Customer existing = new Customer(1L, "John", "john@example.com", null);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(existing));
        doNothing().when(customerRepository).delete(existing);

        assertDoesNotThrow(() -> customerService.deleteCustomer(1L));

        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).delete(existing);
    }

    @Test
    void givenNonExistingCustomerId_whenDeleteCustomer_thenThrowResourceNotFoundException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(1L));

        verify(customerRepository, times(1)).findById(1L);
        verify(customerRepository, times(0)).delete(any());
    }

}
