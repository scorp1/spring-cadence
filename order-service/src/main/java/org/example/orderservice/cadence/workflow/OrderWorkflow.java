package org.example.orderservice.cadence.workflow;

import com.uber.cadence.workflow.WorkflowMethod;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.entity.Order;

import java.util.Optional;


public interface OrderWorkflow {
    @WorkflowMethod(executionStartToCloseTimeoutSeconds = 10, taskList = "orderTaskList")
    OrderDto createOrder(OrderDto order);
}
