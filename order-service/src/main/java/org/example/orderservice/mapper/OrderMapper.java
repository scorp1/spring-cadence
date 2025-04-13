package org.example.orderservice.mapper;

import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderDto orderDto);

    OrderDto toDto(Order order);
}
