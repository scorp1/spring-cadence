package org.example.orderservice.service;

import com.uber.cadence.workflow.Saga;
import java.sql.SQLOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.cadence.config.properties.worker.CadenceWorkerOptions;
import org.example.orderservice.cadence.local.activity.ProductLocalActivity;
import org.example.orderservice.dto.CreateOrderRequestDto;
import org.example.orderservice.dto.CreateOrderResponseDto;
import org.example.orderservice.dto.CustomerDto;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.dto.ProductDto;
import org.example.orderservice.dto.ProductRequestDto;
import org.example.orderservice.entity.Customer;
import org.example.orderservice.entity.Order;
import org.example.orderservice.mapper.OrderMapper;
import org.example.orderservice.repository.OrderRepository;
import org.example.orderservice.service.cadence.CustomerActivityService;
import org.example.orderservice.service.cadence.CustomerLocalActivityService;
import org.example.orderservice.service.cadence.OrderActivityService;
import org.example.orderservice.service.cadence.ProductLocalActivityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final ProductLocalActivityService productLocalActivityService;
    private final CustomerLocalActivityService customerLocalActivityService;
    private final ProductLocalActivity productLocalActivity;
    private final CustomerActivityService customerActivityService;
    private final CadenceWorkerOptions cadenceWorkerOptions;
    private final CompensateRepositoryService compensateRepositoryService;

    public CreateOrderResponseDto createOrder(CreateOrderRequestDto request) {
        final Saga saga = new Saga(new Saga.Options.Builder().build());
        var orderCreate = new OrderDto();
        try {
            var productId = request.getOrderDto().getProductId();
            var quantity = request.getOrderDto().getQuantity();
            var sum = request.getOrderDto().getPrice();
            var customerId = request.getOrderDto().getCustomerId();
            var requestId = request.getRequestId();

            boolean isBuyProduct = productLocalActivityService.buyProduct(productId, requestId, quantity, saga);
            if (!isBuyProduct) {
                return new CreateOrderResponseDto(request.getRequestId(), orderCreate, "Error cancel order, "
                    + "the product is out of stock");
            }

            var resultCustomer = customerActivityService.updateCustomer(getCustomer(customerId, sum),
                cadenceWorkerOptions, saga, requestId);

            if (resultCustomer.getMoney() < 0) {
                log.info("saga compensate!");
                saga.compensate();
                //productLocalActivity.releaseProduct(productId, quantity, requestId);
                compensateRepositoryService.deleteById(requestId);
                return new CreateOrderResponseDto(request.getRequestId(), orderCreate, "Error cancel order, not enough money");
            }

            orderCreate = this.saveOrder(request.getOrderDto());
            compensateRepositoryService.deleteById(requestId);
            log.info("buy sucsess!");

        } catch (Exception e) {
            saga.compensate();
            compensateRepositoryService.deleteById(request.getRequestId());
            return new CreateOrderResponseDto(request.getRequestId(), orderCreate, "Error cancel order, \n"
                + "system failure!");
        }

        return new CreateOrderResponseDto(request.getRequestId(), orderCreate, "Order created successfully");
    }

    public OrderDto saveOrder(OrderDto orderDto) {

        Order order = new Order(null, orderDto.getProductId(),
            orderDto.getCustomerId(), orderDto.getPrice(), orderDto.getProductName(),
            orderDto.getQuantity());
        Order orderCreate = orderRepository.save(order);
        return new OrderDto(orderCreate.getId(),
            orderCreate.getProductId(),
            orderCreate.getCustomerId(),
            orderCreate.getPrice(),
            orderCreate.getProductName(),
            orderCreate.getQuantity());
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



    public List<OrderDto> getOrders() {
        //return orderRepository.findAll().stream().map(orderMapper::toDto).toList();
        var order = new OrderDto();
        return List.of(order);
    }

    public CustomerDto getCustomer(Long customerId, Integer sum) {
        var customer = new CustomerDto();
        customer.setId(customerId);
        customer.setMoney(sum);
        customer.setName("Vasya");

        return customer;
    }
}
