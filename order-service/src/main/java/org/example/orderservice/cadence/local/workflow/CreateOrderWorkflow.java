package org.example.orderservice.cadence.local.workflow;

import com.uber.cadence.workflow.WorkflowMethod;
import org.example.orderservice.dto.CreateOrderRequestDto;
import org.example.orderservice.dto.CreateOrderResponseDto;

public interface CreateOrderWorkflow {
  @WorkflowMethod(name = "order-service:createOrder")
  CreateOrderResponseDto createOrder(CreateOrderRequestDto request);

}
