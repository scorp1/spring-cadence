package org.example.orderservice.cadence.activities;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.cadence.activities.OrderActivities;
import org.example.orderservice.dto.CustomerDto;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.dto.ProductDto;
import org.example.orderservice.entity.Order;
import org.example.orderservice.service.CustomerService;
import org.example.orderservice.service.OrderService;
import org.example.orderservice.service.ProductService;

public class OrderActivitiesImpl implements OrderActivities {
    private final OrderService orderService;
    private final CustomerService customerService;
    private final ProductService productService;

    public OrderActivitiesImpl(OrderService orderService, CustomerService customerService, ProductService productService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Override
    public CustomerDto getCustomer(Long id) {
        return customerService.getCustomer(id);
    }

    @Override
    public ProductDto getProduct(Long id) {
        return productService.getProduct(id);
    }

    @Override
    public CustomerDto chargeCustomer(CustomerDto customer) {
        return customerService.updateCustomer(customer);
    }

    @Override
    public ProductDto reserveProduct(ProductDto product) {
        return productService.updateQuantityProduct(product);
    }

    @Override
    public OrderDto createOrderEntry(OrderDto order) {
        return orderService.saveOrder(order);
    }

    @Override
    public CustomerDto refundCustomer(CustomerDto customer) {
        return customerService.updateCustomer(customer);
    }

    @Override
    public ProductDto releaseProduct(ProductDto product) {
        return productService.updateQuantityProduct(product);
    }
}
