package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.dto.CustomerDto;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.dto.ProductDto;
import org.example.orderservice.entity.Order;
import org.example.orderservice.mapper.OrderMapper;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private  CustomerService customerService;
    private ProductService productService;
    private OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, CustomerService customerService,
        ProductService productService, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.productService = productService;
        this.orderMapper = orderMapper;
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

        Order orderSaved = orderRepository.save(orderMapper.toEntity(order));

        return orderMapper.toDto(orderSaved);
    }

    public List<OrderDto> getOrders() {
        return orderRepository.findAll().stream().map(orderMapper::toDto).toList();
    }
}
