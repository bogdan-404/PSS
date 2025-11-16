package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import com.example.fooddelivery.domain.OrderType;
import com.example.fooddelivery.dto.OrderCreationRequest;
import org.springframework.stereotype.Component;

/**
 * Design Pattern: Factory Method - Concrete Implementation
 * 
 * Creates group orders that may have special pricing or delivery arrangements.
 */
@Component
public class GroupOrderFactory implements OrderFactory {
    @Override
    public Order createOrder(OrderCreationRequest request) {
        Order order = new Order();
        order.setType(OrderType.GROUP);
        order.setPriority(request.getPriority());
        order.setExtraPackaging(request.getExtraPackaging());
        order.setInsurance(request.getInsurance());
        order.setPaymentMethod(request.getPaymentMethod());
        // Group orders might have scheduled time
        order.setScheduledFor(request.getScheduledFor());
        return order;
    }
}

