package org.example.orderservice.cadence.workflow;

import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.workflow.Workflow;
import org.example.orderservice.cadence.activities.OrderActivities;
import org.example.orderservice.dto.CustomerDto;
import org.example.orderservice.dto.OrderDto;
import org.example.orderservice.dto.OrderResponseDto;
import org.example.orderservice.dto.ProductDto;

import java.time.Duration;

//@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OrderWorkflowImpl implements OrderWorkflow {
    private final OrderActivities activities = Workflow.newActivityStub(
            OrderActivities.class,
            new ActivityOptions.Builder()
                    .setScheduleToCloseTimeout(Duration.ofMinutes(5))
                    .build());
    @Override
    public OrderResponseDto createOrder(OrderDto order) {

        ProductDto product = activities.getProduct(order.getProductId());
        CustomerDto customer = activities.getCustomer(order.getCustomerId());
        OrderResponseDto orderResponse = new OrderResponseDto();
        orderResponse.setOrder(new OrderDto());

        boolean reserved = false;
        boolean charged = false;

        try {
            if (product.getCount() < order.getQuantity()) {
                orderResponse.setErrorText("Нет товара");

                return orderResponse;
            }
            product.setCount(product.getCount() - order.getQuantity());
            activities.reserveProduct(product);
            reserved = true;

            if (customer.getMoney() < order.getPrice() * order.getQuantity()) {
                orderResponse.setErrorText("Недостаточно средств у пользователя");
                product.setCount(product.getCount() + order.getQuantity());
                activities.releaseProduct(product);

                return orderResponse;
            }
            customer.setMoney(customer.getMoney() - order.getPrice() * order.getQuantity());
            activities.chargeCustomer(customer);
            charged = true;

            OrderDto newOrder = activities.createOrderEntry(order);
            orderResponse.setOrder(newOrder);

            return orderResponse;
        } catch (Exception e) {
            if (charged) {
                customer.setMoney(customer.getMoney() + order.getPrice() * order.getQuantity());
                activities.refundCustomer(customer);
            }

            if (reserved) {
                product.setCount(product.getCount() + order.getQuantity());
                activities.releaseProduct(product);
            }
            orderResponse.setErrorText("Ошибка выполенния заказа, попробуйте еще раз");

            return orderResponse;
        }
    }
}
