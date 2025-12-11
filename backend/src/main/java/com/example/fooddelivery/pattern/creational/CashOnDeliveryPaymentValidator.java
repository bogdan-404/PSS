package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design Pattern: Abstract Factory - Concrete Product
 * 
 * Implements payment validation for Cash on Delivery platform.
 */
@Component
public class CashOnDeliveryPaymentValidator implements PaymentValidator {
    @Override
    public boolean validatePaymentDetails(Order order, BigDecimal amount) {
        // COD-specific validation: no upfront payment needed, just verify order is valid
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        System.out.println("Cash on Delivery: Validating order " + order.getId() + " for COD payment");
        return true;
    }

    @Override
    public boolean validateRefundEligibility(Order order, BigDecimal amount) {
        // COD refunds are handled differently - usually no refund needed as payment happens on delivery
        System.out.println("Cash on Delivery: Validating refund eligibility for order " + order.getId());
        return false; // COD orders typically don't need refunds as payment is on delivery
    }

    @Override
    public String getValidatorName() {
        return "Cash on Delivery Payment Validator";
    }
}

