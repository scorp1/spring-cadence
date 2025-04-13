package org.example.orderservice.api;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.cadence.service.CadenceOrderService;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.entity.Order;
import org.example.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final CadenceOrderService cadenceOrderService;

    @PostMapping()
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto order) {
        if (order != null) {
            OrderDto savedOrder = orderService.process(order);
            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/cadence/create")
    public ResponseEntity<OrderDto> createOrderWithCadence(@RequestBody OrderDto order) {
        OrderDto newOrder = cadenceOrderService.createOrderCadence(order);
        if (newOrder != null) {
            return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
