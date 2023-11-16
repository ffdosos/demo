package com.example.csvreader.controller;

import com.example.csvreader.entity.Customer;
import com.example.csvreader.repository.CustomerRepository;
import com.example.csvreader.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/customers")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PostMapping
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		Customer savedCustomer = customerService.createCustomer(customer);
		return ResponseEntity.ok(savedCustomer);
	}

	@GetMapping("/{customerRef}")
	public ResponseEntity<Customer> getCustomer(@PathVariable String customerRef) {
		Optional<Customer> customer = customerService.getCustomer(customerRef);
		return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
