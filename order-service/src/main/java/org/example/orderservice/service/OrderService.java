package org.example.orderservice.service;

import com.uber.cadence.workflow.Saga;
import lombok.RequiredArgsConstructor;
import org.example.orderservice.cadence.config.properties.worker.CadenceWorkerOptions;
import org.example.orderservice.dto.CreateOrderRequestDto;
import org.example.orderservice.dto.CreateOrderResponseDto;
import org.example.orderservice.dto.CustomerDto;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.dto.ProductDto;
import org.example.orderservice.dto.ProductRequestDto;
import org.example.orderservice.entity.Order;
import org.example.orderservice.mapper.OrderMapper;
import org.example.orderservice.repository.OrderRepository;
import org.example.orderservice.service.cadence.OrderActivityService;
import org.example.orderservice.service.cadence.ProductLocalActivityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    //private final OrderMapper orderMapper;
    private final OrderActivityService orderActivityService;
    private final CadenceWorkerOptions cadenceWorkerOptions;
    private final ProductLocalActivityService productLocalActivityService;

//    public OrderService(OrderRepository orderRepository, CustomerService customerService,
//        ProductService productService, OrderActivityService orderActivityService, CadenceWorkerOptions cadenceWorkerOptions) {
//        this.orderRepository = orderRepository;
//        this.customerService = customerService;
//        this.productService = productService;
//        this.orderActivityService = orderActivityService;
//        this.cadenceWorkerOptions = cadenceWorkerOptions;
//    }

    public CreateOrderResponseDto createOrder(CreateOrderRequestDto request) {
        final Saga saga = new Saga(new Saga.Options.Builder().build());
        var product = new ProductDto(request.getOrderDto().getProductId(),
            request.getOrderDto().getProductName(), "4", "1",
            request.getOrderDto().getQuantity());
        var productRequest = new ProductRequestDto(request.getRequestId(), product);
        var isBuy = productLocalActivityService.buyProduct(product, saga);
        if (isBuy) {
            saga.compensate();
        }

        return new CreateOrderResponseDto(request.getRequestId(), request.getOrderDto());
    }

    public OrderDto process(OrderDto order) {
        ProductDto product = productService.getProduct(order.getProductId());
        if (product != null && product.getCount() > order.getQuantity()) {
            product.setCount(product.getCount() - order.getQuantity());
            productService.updateQuantityProduct(product);
        }
        CustomerDto customer = customerService.getCustomer(order.getCustomerId());
        if (customer != null && customer.getMoney() > order.getPrice()) {
            customer.setMoney(customer.getMoney() - order.getPrice());
            customerService.updateCustomer(customer);
        }

        return saveOrder(order);
    }

    public OrderDto saveOrder(OrderDto order) {

        //Order orderSaved = orderRepository.save(orderMapper.toEntity(order));

        //return orderMapper.toDto(orderSaved);
        return new OrderDto();
    }

    public List<OrderDto> getOrders() {
        //return orderRepository.findAll().stream().map(orderMapper::toDto).toList();
        var order = new OrderDto();
        return List.of(order);
    }
}
