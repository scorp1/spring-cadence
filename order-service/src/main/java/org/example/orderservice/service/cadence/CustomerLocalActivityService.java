package org.example.orderservice.service.cadence;

import com.uber.cadence.workflow.Saga;
import com.uber.cadence.workflow.Workflow;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.cadence.local.activity.CustomerLocalActivity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerLocalActivityService {

  public Boolean withdrawingWallet(Long customerId, UUID requestId, Integer sum, Saga saga) {
    CustomerLocalActivity activity = Workflow.newLocalActivityStub(
        CustomerLocalActivity.class);
    Boolean isBuy = activity.chargeCustomer(customerId, sum, requestId);
    saga.addCompensation(activity::refundCustomer, customerId, sum);

    return isBuy;
  }
}
