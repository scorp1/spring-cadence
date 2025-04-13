package org.example.customerservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.customerservice.entity.Customer;
import org.example.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Optional<Customer> findCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional
    public Customer processWallet(Customer customer) {
        return customerRepository.save(customer);
    }
}
