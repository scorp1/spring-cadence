package org.example.customerservice.api;

import lombok.RequiredArgsConstructor;
import org.example.customerservice.entity.Customer;
import org.example.customerservice.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customer/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("{id}")
    public ResponseEntity<Customer> getConsumer(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findCustomerById(id);
        if (customer.isPresent()) {
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("buy")
    public ResponseEntity<Customer> processBuy(@RequestBody Customer customer) {
        Customer customerNew = customerService.processWallet(customer);

        return new ResponseEntity<>(customerNew, HttpStatus.OK);
    }
}
