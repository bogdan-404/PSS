package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design Pattern: Abstract Factory - Concrete Product
 * 
 * Implements payment validation for PayPal platform.
 */
@Component
public class PaypalPaymentValidator implements PaymentValidator {
    @Override
    public boolean validatePaymentDetails(Order order, BigDecimal amount) {
        // PayPal-specific validation: check PayPal account, amount limits, etc.
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        if (amount.compareTo(new BigDecimal("50000")) > 0) {
            // PayPal has different limits than Stripe
            return false;
        }
        System.out.println("PayPal: Validating payment details for order " + order.getId() + ", amount: " + amount);
        return true;
    }

    @Override
    public boolean validateRefundEligibility(Order order, BigDecimal amount) {
        // PayPal-specific refund validation
        System.out.println("PayPal: Validating refund eligibility for order " + order.getId());
        return true;
    }

    @Override
    public String getValidatorName() {
        return "PayPal Payment Validator";
    }
}

