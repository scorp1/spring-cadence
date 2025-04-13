package org.example.orderservice.cadence.workflow;

import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.workflow.Workflow;
import org.example.orderservice.cadence.activities.OrderActivities;
import org.example.orderservice.dto.CustomerDto;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.dto.ProductDto;
import org.springframework.context.annotation.Scope;

import java.time.Duration;

//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OrderWorkflowImpl implements OrderWorkflow {
    private final OrderActivities activities = Workflow.newActivityStub(
            OrderActivities.class,
            new ActivityOptions.Builder()
                    .setScheduleToCloseTimeout(Duration.ofMinutes(5))
                    .build());
    @Override
    public OrderDto createOrder(OrderDto order) {

        ProductDto product = activities.getProduct(order.getProductId());
        CustomerDto customer = activities.getCustomer(order.getCustomerId());

        if (product.getCount() < order.getQuantity() ||
        customer.getMoney() < order.getPrice() * order.getQuantity()) {

            return null;
        }
        boolean reserved = false;
        boolean charged = false;

        try {
            product.setCount(product.getCount() - order.getQuantity());
            activities.reserveProduct(product);
            reserved = true;

            customer.setMoney(customer.getMoney() - order.getPrice() * order.getQuantity());
            activities.chargeCustomer(customer);
            charged = true;

            OrderDto newOrder = activities.createOrderEntry(order);

            return newOrder;
        } catch (Exception e) {
            if (charged) {
                customer.setMoney(customer.getMoney() + order.getPrice() * order.getQuantity());
                activities.refundCustomer(customer);
            }

            if (reserved) {
                product.setCount(product.getCount() + order.getQuantity());
                activities.releaseProduct(product);
            }
            return null;
        }
    }
}
