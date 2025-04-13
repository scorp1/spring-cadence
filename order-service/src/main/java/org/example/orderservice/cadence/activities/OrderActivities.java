package org.example.orderservice.cadence.activities;

import com.uber.cadence.activity.ActivityMethod;
import org.example.orderservice.dto.CustomerDto;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.dto.ProductDto;
import org.example.orderservice.entity.Order;

public interface OrderActivities {

    @ActivityMethod(name = "chargeCustomer")
    CustomerDto chargeCustomer(CustomerDto customer);
    @ActivityMethod(name = "refundCustomer")
    CustomerDto refundCustomer(CustomerDto customer);
    @ActivityMethod(name = "reserveProduct")
    ProductDto reserveProduct(ProductDto product);

    @ActivityMethod(name = "releaseProduct")
    ProductDto releaseProduct(ProductDto productDto);

    @ActivityMethod(name = "getProduct")
    ProductDto getProduct(Long id);

    @ActivityMethod(name = "getCustomer")
    CustomerDto getCustomer(Long id);

    @ActivityMethod(name = "createOrderEntry")
    OrderDto createOrderEntry(OrderDto order);


}
