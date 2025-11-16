package com.example.fooddelivery.pattern.structural;

import com.example.fooddelivery.domain.Order;

import java.math.BigDecimal;

/**
 * Design Pattern: Decorator - Concrete Decorator
 * 
 * Adds extra packaging fee per item to the base price.
 */
public class ExtraPackagingPriceDecorator implements PriceCalculator {
    private static final BigDecimal PACKAGING_FEE_PER_ITEM = new BigDecimal("1.50");
    private final PriceCalculator wrapped;

    public ExtraPackagingPriceDecorator(PriceCalculator wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public BigDecimal calculatePrice(Order order) {
        BigDecimal basePrice = wrapped.calculatePrice(order);
        if (Boolean.TRUE.equals(order.getExtraPackaging())) {
            int itemCount = order.getItems().stream()
                .mapToInt(item -> item.getQuantity())
                .sum();
            BigDecimal packagingFee = PACKAGING_FEE_PER_ITEM.multiply(BigDecimal.valueOf(itemCount));
            return basePrice.add(packagingFee);
        }
        return basePrice;
    }
}

