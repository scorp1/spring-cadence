package org.example.orderservice.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.entity.Customer;
import org.example.orderservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerRepositoryService {

  private final CustomerRepository customerRepository;

  public Optional<Customer> findCustomerById(Long id) {
    return customerRepository.findById(id);
  }

  @Transactional
  public Customer processWallet(Customer customer) {
    return customerRepository.save(customer);
  }
}
