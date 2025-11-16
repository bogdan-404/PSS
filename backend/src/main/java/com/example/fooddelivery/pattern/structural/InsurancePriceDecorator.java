package com.example.fooddelivery.pattern.structural;

import com.example.fooddelivery.domain.Order;

import java.math.BigDecimal;

/**
 * Design Pattern: Decorator - Concrete Decorator
 * 
 * Adds insurance fee as a percentage of the base price.
 */
public class InsurancePriceDecorator implements PriceCalculator {
    private static final BigDecimal INSURANCE_PERCENTAGE = new BigDecimal("0.03"); // 3%
    private final PriceCalculator wrapped;

    public InsurancePriceDecorator(PriceCalculator wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public BigDecimal calculatePrice(Order order) {
        BigDecimal basePrice = wrapped.calculatePrice(order);
        if (Boolean.TRUE.equals(order.getInsurance())) {
            BigDecimal insuranceFee = basePrice.multiply(INSURANCE_PERCENTAGE);
            return basePrice.add(insuranceFee);
        }
        return basePrice;
    }
}

