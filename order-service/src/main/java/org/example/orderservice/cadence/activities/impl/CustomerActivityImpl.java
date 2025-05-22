package org.example.orderservice.cadence.activities.impl;

import org.example.orderservice.cadence.activities.CustomerActivity;
import org.example.orderservice.dto.CustomerRequestDto;
import org.example.orderservice.dto.CustomerResponseDto;
import org.example.orderservice.service.CustomerService;

public class CustomerActivityImpl implements CustomerActivity {

  private final CustomerService customerService;

  public CustomerActivityImpl(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Override
  public CustomerResponseDto chargeCustomer(CustomerRequestDto request) {
    var customer = customerService.updateCustomer(request.getCustomer());

    return new CustomerResponseDto(request.getRequestId(), customer);
  }

  @Override
  public CustomerResponseDto refundCustomer(CustomerRequestDto request) {
    var customer = customerService.updateCustomer(request.getCustomer());

    return new CustomerResponseDto(request.getRequestId(), customer);
  }
}
