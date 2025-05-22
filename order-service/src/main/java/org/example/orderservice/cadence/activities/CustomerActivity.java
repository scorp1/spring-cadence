package org.example.orderservice.cadence.activities;

import com.uber.cadence.activity.ActivityMethod;
import org.example.orderservice.dto.CustomerDto;
import org.example.orderservice.dto.CustomerRequestDto;
import org.example.orderservice.dto.CustomerResponseDto;

public interface CustomerActivity {

  @ActivityMethod(name = "customer-service:chargeCustomer")
  CustomerResponseDto chargeCustomer(CustomerRequestDto request);
  @ActivityMethod(name = "customer-service:refundCustomer")
  CustomerResponseDto refundCustomer(CustomerRequestDto request);
}
