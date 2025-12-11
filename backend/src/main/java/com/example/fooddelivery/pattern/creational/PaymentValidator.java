package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import java.math.BigDecimal;

/**
 * Design Pattern: Abstract Factory - Product Interface
 * 
 * Defines the interface for payment validation.
 * Each concrete validator implements validation logic specific to its payment platform.
 */
public interface PaymentValidator {
    boolean validatePaymentDetails(Order order, BigDecimal amount);
    boolean validateRefundEligibility(Order order, BigDecimal amount);
    String getValidatorName();
}

