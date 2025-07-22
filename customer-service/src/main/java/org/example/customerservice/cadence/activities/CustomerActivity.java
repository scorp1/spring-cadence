package org.example.customerservice.cadence.activities;

import com.uber.cadence.activity.ActivityMethod;
import java.util.UUID;
import org.example.customerservice.dto.CustomerDto;

public interface CustomerActivity {
  @ActivityMethod(name = "customer-service:chargeCustomer")
  CustomerDto chargeCustomer(CustomerDto customerDto, UUID requestId);
  @ActivityMethod(name = "customer-service:refundCustomer")
  CustomerDto refundCustomer(CustomerDto customerDto, UUID requestId);
}
