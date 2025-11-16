package com.example.fooddelivery.pattern.structural;

import com.example.fooddelivery.domain.Order;
import com.example.fooddelivery.domain.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design Pattern: Decorator - Concrete Component
 * 
 * Base price calculator that sums up menu item prices.
 * This is the core calculation that decorators will enhance.
 */
@Component
public class BasePriceCalculator implements PriceCalculator {
    @Override
    public BigDecimal calculatePrice(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : order.getItems()) {
            total = total.add(item.getFinalPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }
}

