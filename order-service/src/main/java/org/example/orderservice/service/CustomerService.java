package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.client.CustomerClient;
import org.example.orderservice.dto.CustomerDto;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerClient customerClient;

    public CustomerService(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    public CustomerDto getCustomer(Long id) {
        return customerClient.getCustomerById(id);
    }

    public CustomerDto updateCustomer(CustomerDto customer) {
        return customerClient.updateCustomer(customer);
    }

}
