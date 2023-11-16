package com.example.csvreader.service.impl;

import com.example.csvreader.entity.Customer;
import com.example.csvreader.repository.CustomerRepository;
import com.example.csvreader.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService = new CustomerServiceImpl();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setCustomerRef("12345");
        customer.setCustomerName("John Doe");

        when(customerRepository.save(customer)).thenReturn(customer);

        // Act
        Customer savedCustomer = customerService.createCustomer(customer);

        // Assert
        assertEquals("12345", savedCustomer.getCustomerRef());
        assertEquals("John Doe", savedCustomer.getCustomerName());

        // Verify that the save method was called once
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testGetCustomer() {
        // Arrange
        String customerRef = "12345";
        Customer customer = new Customer();
        customer.setCustomerRef(customerRef);
        customer.setCustomerName("John Doe");

        when(customerRepository.findById(customerRef)).thenReturn(Optional.of(customer));

        // Act
        Optional<Customer> retrievedCustomer = customerService.getCustomer(customerRef);

        // Assert
        assertEquals(customerRef, retrievedCustomer.get().getCustomerRef());
        assertEquals("John Doe", retrievedCustomer.get().getCustomerName());

        // Verify that the findById method was called once
        verify(customerRepository, times(1)).findById(customerRef);
    }
}
