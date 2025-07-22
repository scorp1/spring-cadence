package org.example.orderservice.cadence.activities;

import com.uber.cadence.activity.ActivityMethod;
import java.util.UUID;
import org.example.orderservice.dto.CustomerDto;
import org.example.orderservice.dto.CustomerRequestDto;
import org.example.orderservice.dto.CustomerResponseDto;

public interface CustomerActivity {

  @ActivityMethod(name = "customer-service:chargeCustomer")
  CustomerDto chargeCustomer(CustomerDto request, UUID requestId);
  @ActivityMethod(name = "customer-service:refundCustomer")
  CustomerDto refundCustomer(CustomerDto request, UUID requestId);
}
