package org.example.orderservice.cadence.local.activity;

import com.uber.cadence.activity.ActivityMethod;
import org.example.orderservice.dto.CustomerDto;

public interface CustomerLocalActivity {

  @ActivityMethod(name = "chargeCustomerLocal")
  Boolean chargeCustomer(Long customerId, Integer sum);

  @ActivityMethod(name = "refundCustomerLocal")
  void refundCustomer(Long customerId, Integer sum);

}
