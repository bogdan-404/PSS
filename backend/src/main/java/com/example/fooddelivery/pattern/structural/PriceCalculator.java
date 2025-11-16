package com.example.fooddelivery.pattern.structural;

import com.example.fooddelivery.domain.Order;
import java.math.BigDecimal;

/**
 * Design Pattern: Decorator - Component Interface
 * 
 * Base interface for price calculation.
 * Decorators can wrap this interface to add additional pricing logic.
 */
public interface PriceCalculator {
    BigDecimal calculatePrice(Order order);
}

