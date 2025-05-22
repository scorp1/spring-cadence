package org.example.orderservice.api;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.cadence.service.CadenceOrderService;
import org.example.orderservice.dto.CreateOrderRequestDto;
import org.example.orderservice.dto.CreateOrderResponseDto;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.dto.OrderResponseDto;
import org.example.orderservice.service.OrderService;
import org.example.orderservice.service.cadence.CadenceWorkflowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CadenceOrderService cadenceOrderService;
    private final CadenceWorkflowService cadenceWorkflowService;

//  public OrderController(CadenceOrderService cadenceOrderService,
//      OrderService orderService, CadenceWorkflowService cadenceWorkflowService) {
//    this.cadenceOrderService = cadenceOrderService;
//    this.orderService = orderService;
//    this.cadenceWorkflowService = cadenceWorkflowService;
//  }
    @PostMapping()
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto order) {
        if (order != null) {
            OrderDto savedOrder = orderService.process(order);
            return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/cadence/create")
    public ResponseEntity<OrderResponseDto> createOrderWithCadence(@RequestBody OrderDto order) {
//        OrderResponseDto newOrder = cadenceOrderService.createOrderCadence(order);

       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

  @PostMapping("/cadence/create-order")
  public ResponseEntity<CreateOrderResponseDto> createOrderCadence(
      @RequestBody CreateOrderRequestDto request) {
    CreateOrderResponseDto orderResponse = cadenceWorkflowService.createOrder(request);

    return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
  }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
