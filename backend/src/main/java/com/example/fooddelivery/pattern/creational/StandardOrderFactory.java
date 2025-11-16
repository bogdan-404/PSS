package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import com.example.fooddelivery.domain.OrderType;
import com.example.fooddelivery.dto.OrderCreationRequest;
import org.springframework.stereotype.Component;

/**
 * Design Pattern: Factory Method - Concrete Implementation
 * 
 * Creates standard orders that are processed immediately.
 */
@Component
public class StandardOrderFactory implements OrderFactory {
    @Override
    public Order createOrder(OrderCreationRequest request) {
        Order order = new Order();
        order.setType(OrderType.STANDARD);
        order.setPriority(request.getPriority());
        order.setExtraPackaging(request.getExtraPackaging());
        order.setInsurance(request.getInsurance());
        order.setPaymentMethod(request.getPaymentMethod());
        // Standard orders don't have scheduled time
        order.setScheduledFor(null);
        return order;
    }
}

