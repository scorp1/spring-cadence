package org.example.orderservice.service.cadence;

import org.example.orderservice.dto.CreateOrderRequestDto;
import org.example.orderservice.dto.CreateOrderResponseDto;

public interface CadenceWorkflowService {
  CreateOrderResponseDto createOrder(CreateOrderRequestDto request);

}
