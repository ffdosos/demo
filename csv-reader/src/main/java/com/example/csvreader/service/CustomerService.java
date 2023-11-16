package com.example.csvreader.service;

import com.example.csvreader.entity.Customer;

import java.util.Optional;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Optional<Customer> getCustomer(String customerRef);
}
