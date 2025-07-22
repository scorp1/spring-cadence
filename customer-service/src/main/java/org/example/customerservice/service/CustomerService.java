package org.example.customerservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.customerservice.dto.CustomerDto;
import org.example.customerservice.entity.Customer;
import org.example.customerservice.repository.CompensateRepository;
import org.example.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CompensateRepository compensateRepository;

    public Optional<CustomerDto> findCustomerById(Long id) {
        var customerDto = new CustomerDto();
        var customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerDto.setId(customer.get().getId());
            customerDto.setMoney(customer.get().getMoney());
            customerDto.setName(customer.get().getName());
        }
        return Optional.of(customerDto);
    }

    @Transactional
    public CustomerDto updateCustomer(CustomerDto customerDto) {
        var customerEntity = new Customer();
        customerEntity.setId(customerDto.getId());
        customerEntity.setName(customerDto.getName());
        customerEntity.setMoney(customerDto.getMoney());
        customerRepository.save(customerEntity);

        return customerDto;
    }
}
