package org.example.orderservice.service.cadence;

import com.uber.cadence.workflow.Saga;
import com.uber.cadence.workflow.Workflow;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.cadence.local.activity.CustomerLocalActivity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerLocalActivityService {

  public Boolean withdrawingWallet(Long customerId, Integer sum, Saga saga) {
    CustomerLocalActivity activity = Workflow.newLocalActivityStub(
        CustomerLocalActivity.class);
    Boolean isBuy = activity.chargeCustomer(customerId, sum);
    saga.addCompensation(activity::refundCustomer, customerId, sum);

    return isBuy;
  }
}
