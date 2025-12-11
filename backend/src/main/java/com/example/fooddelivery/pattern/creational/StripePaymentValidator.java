package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design Pattern: Abstract Factory - Concrete Product
 * 
 * Implements payment validation for Stripe platform.
 */
@Component
public class StripePaymentValidator implements PaymentValidator {
    @Override
    public boolean validatePaymentDetails(Order order, BigDecimal amount) {
        // Stripe-specific validation: check card details, amount limits, etc.
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (amount.compareTo(new BigDecimal("10000")) > 0) {
            // Stripe has a maximum transaction limit
            return false;
        }
        System.out.println("Stripe: Validating payment details for order " + order.getId() + ", amount: " + amount);
        return true;
    }

    @Override
    public boolean validateRefundEligibility(Order order, BigDecimal amount) {
        // Stripe-specific refund validation: check if order is refundable, time limits, etc.
        System.out.println("Stripe: Validating refund eligibility for order " + order.getId());
        return true;
    }

    @Override
    public String getValidatorName() {
        return "Stripe Payment Validator";
    }
}

