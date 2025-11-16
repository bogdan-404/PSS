package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import com.example.fooddelivery.domain.OrderType;
import com.example.fooddelivery.dto.OrderCreationRequest;
import org.springframework.stereotype.Component;

/**
 * Design Pattern: Factory Method - Concrete Implementation
 * 
 * Creates scheduled orders that are delivered at a specific future time.
 */
@Component
public class ScheduledOrderFactory implements OrderFactory {
    @Override
    public Order createOrder(OrderCreationRequest request) {
        Order order = new Order();
        order.setType(OrderType.SCHEDULED);
        order.setPriority(request.getPriority());
        order.setExtraPackaging(request.getExtraPackaging());
        order.setInsurance(request.getInsurance());
        order.setPaymentMethod(request.getPaymentMethod());
        order.setScheduledFor(request.getScheduledFor());
        return order;
    }
}

