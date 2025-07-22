package org.example.customerservice.cadence.workflow;

import com.uber.cadence.workflow.WorkflowMethod;
import org.example.customerservice.dto.CustomerDto;


public interface CustomerWorkflow {
    @WorkflowMethod(executionStartToCloseTimeoutSeconds = 10, taskList = "customerTaskList")
    CustomerDto updateCustomer(CustomerDto customerDto);
}
