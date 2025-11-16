package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import java.math.BigDecimal;

/**
 * Design Pattern: Abstract Factory - Product Interface
 * 
 * Defines the interface for payment processing providers.
 * Each concrete provider implements payment operations differently.
 */
public interface PaymentProvider {
    boolean authorizePayment(Order order, BigDecimal amount);
    boolean capturePayment(Order order, BigDecimal amount);
    boolean refund(Order order, BigDecimal amount);
    String getProviderName();
}

