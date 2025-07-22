package org.example.customerservice.cadence.local.workflow;

import com.uber.cadence.workflow.WorkflowMethod;
import org.example.customerservice.dto.CustomerDto;

public interface CustomerWorkflow {

  @WorkflowMethod(name = "customer-service:updateCustomer")
  CustomerDto updateCustomer(CustomerDto customerDto);
}
