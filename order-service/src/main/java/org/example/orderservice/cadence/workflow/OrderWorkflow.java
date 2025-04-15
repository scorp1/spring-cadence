package org.example.orderservice.cadence.workflow;

import com.uber.cadence.workflow.WorkflowMethod;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.dto.OrderResponseDto;


public interface OrderWorkflow {
    @WorkflowMethod(executionStartToCloseTimeoutSeconds = 10, taskList = "orderTaskList")
    OrderResponseDto createOrder(OrderDto order);
}
