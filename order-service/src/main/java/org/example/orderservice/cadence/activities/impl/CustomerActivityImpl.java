package org.example.orderservice.cadence.activities.impl;

import org.example.orderservice.cadence.activities.CustomerActivity;
import org.example.orderservice.dto.CustomerDto;
import org.example.orderservice.dto.CustomerRequestDto;
import org.example.orderservice.dto.CustomerResponseDto;
import org.example.orderservice.service.CustomerService;

public class CustomerActivityImpl {

  private final CustomerService customerService;

  public CustomerActivityImpl(CustomerService customerService) {
    this.customerService = customerService;
  }


  public CustomerDto chargeCustomer(CustomerDto customerDto) {
    var customer = customerService.updateCustomer(customerDto);

    return customer;
  }

  public CustomerDto refundCustomer(CustomerDto customerDto) {
    var customer = customerService.updateCustomer(customerDto);

    return customer;
  }
}
