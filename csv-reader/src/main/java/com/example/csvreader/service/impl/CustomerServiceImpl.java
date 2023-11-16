package com.example.csvreader.service.impl;

import com.example.csvreader.entity.Customer;
import com.example.csvreader.repository.CustomerRepository;
import com.example.csvreader.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> getCustomer(String customerRef) {
        return customerRepository.findById(customerRef);
    }

}
