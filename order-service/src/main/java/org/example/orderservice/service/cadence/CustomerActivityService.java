package org.example.orderservice.service.cadence;

import com.uber.cadence.workflow.Saga;
import com.uber.cadence.workflow.Workflow;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.cadence.activities.CustomerActivity;
import org.example.orderservice.cadence.config.CadenceConfigClient;
import org.example.orderservice.cadence.config.properties.activity.CadenceActivityProperties;
import org.example.orderservice.cadence.config.properties.worker.CadenceWorkerOptions;
import org.example.orderservice.cadence.config.properties.workflow.CadenceWorkflowProperties;
import org.example.orderservice.dto.CustomerDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerActivityService {

  private final CadenceConfigClient cadenceConfigClient;
  private final CadenceActivityProperties cadenceActivityProperties;
  private final CadenceWorkflowProperties cadenceWorkflowsProperties;

  public CustomerDto updateCustomer(CustomerDto customerDto, CadenceWorkerOptions workerOptions,
      Saga saga, UUID requestId) {

    var customerActivity = Workflow.newActivityStub(
        CustomerActivity.class,
        cadenceConfigClient.createExternalActivityOptions(workerOptions.getCustomerTaskList(),
            cadenceActivityProperties.getCustomerActivity()));

    var updateUserResponse = customerActivity.chargeCustomer(customerDto, requestId);
    saga.addCompensation(customerActivity::refundCustomer, customerDto, requestId);

    return updateUserResponse;
  }
}
