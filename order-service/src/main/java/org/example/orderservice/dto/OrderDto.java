package org.example.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long productId;
    private Long customerId;
    private Integer price;
    private String productName;
    private Integer quantity;
}
