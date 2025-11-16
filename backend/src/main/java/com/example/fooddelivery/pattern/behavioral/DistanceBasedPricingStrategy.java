package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design Pattern: Strategy - Concrete Strategy
 * 
 * Adds delivery fee based on the distance of the delivery route.
 */
@Component
public class DistanceBasedPricingStrategy implements PricingStrategy {
    private static final BigDecimal FEE_PER_KM = new BigDecimal("0.50");

    @Override
    public BigDecimal apply(BigDecimal basePrice, Order order) {
        if (order.getDeliveryRoute() != null && order.getDeliveryRoute().getDistanceKm() != null) {
            double distance = order.getDeliveryRoute().getDistanceKm();
            BigDecimal distanceFee = FEE_PER_KM.multiply(BigDecimal.valueOf(distance));
            return basePrice.add(distanceFee);
        }
        return basePrice;
    }
}

