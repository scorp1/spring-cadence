package org.example.orderservice.client;

import org.example.orderservice.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "customerService", url = "http://localhost:8086/api/customer/")
public interface CustomerClient {

    @GetMapping("{id}")
    public CustomerDto getCustomerById(@PathVariable("id") Long id);

    @PostMapping("buy")
    public CustomerDto updateCustomer(@RequestBody CustomerDto customer);
}
