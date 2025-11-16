package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design Pattern: Strategy - Concrete Strategy
 * 
 * Applies promotional discount (for demo, applies 10% discount to group orders).
 */
@Component
public class PromotionalPricingStrategy implements PricingStrategy {
    private static final BigDecimal DISCOUNT_PERCENTAGE = new BigDecimal("0.10");

    @Override
    public BigDecimal apply(BigDecimal basePrice, Order order) {
        // Apply discount for group orders
        if (order.getType() != null && order.getType().name().equals("GROUP")) {
            BigDecimal discount = basePrice.multiply(DISCOUNT_PERCENTAGE);
            return basePrice.subtract(discount);
        }
        return basePrice;
    }
}

