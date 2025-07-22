package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.entity.Order;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderRepositoryService {

  private final OrderRepository orderRepository;

  public OrderDto saveOrder(OrderDto orderDto) {
    Order order = new Order(null, orderDto.getProductId(),
        orderDto.getCustomerId(), orderDto.getPrice(), orderDto.getProductName(),
        orderDto.getQuantity());
    var orderCreate = orderRepository.save(order);
    OrderDto orderDtoCreate = new OrderDto(orderCreate.getId(),
        orderCreate.getProductId(), orderCreate.getCustomerId(),
        orderCreate.getPrice(), orderCreate.getProductName(), orderCreate.getQuantity());
    return orderDtoCreate;
  }

}
