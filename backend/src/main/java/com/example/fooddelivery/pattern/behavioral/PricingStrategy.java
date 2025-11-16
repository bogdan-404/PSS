package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.domain.Order;
import java.math.BigDecimal;

/**
 * Design Pattern: Strategy
 * 
 * Strategy interface for applying different pricing rules.
 * Each concrete strategy implements a specific pricing algorithm.
 */
public interface PricingStrategy {
    BigDecimal apply(BigDecimal basePrice, Order order);
}

