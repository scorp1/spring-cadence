package org.example.orderservice.cadence.workflow;

import com.uber.cadence.workflow.Saga;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.cadence.local.workflow.CreateOrderWorkflow;
import org.example.orderservice.dto.CreateOrderRequestDto;
import org.example.orderservice.dto.CreateOrderResponseDto;
import org.example.orderservice.service.OrderService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class CreateOrderWorkflowImpl implements CreateOrderWorkflow {
  private final OrderService orderService;

  @Override
  public CreateOrderResponseDto createOrder(CreateOrderRequestDto request) {
    var orderResponse = orderService.createOrder(request);

    return orderResponse;
  }

}
