package com.example.fooddelivery.pattern.structural;

import com.example.fooddelivery.domain.Order;

import java.math.BigDecimal;

/**
 * Design Pattern: Decorator - Concrete Decorator
 * 
 * Adds priority delivery fee to the base price.
 * Wraps another PriceCalculator and enhances its calculation.
 */
public class PriorityDeliveryPriceDecorator implements PriceCalculator {
    private static final BigDecimal PRIORITY_FEE = new BigDecimal("5.00");
    private final PriceCalculator wrapped;

    public PriorityDeliveryPriceDecorator(PriceCalculator wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public BigDecimal calculatePrice(Order order) {
        BigDecimal basePrice = wrapped.calculatePrice(order);
        if (order.isPriority()) {
            return basePrice.add(PRIORITY_FEE);
        }
        return basePrice;
    }
}

