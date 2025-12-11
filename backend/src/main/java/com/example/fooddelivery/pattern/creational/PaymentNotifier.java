package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import java.math.BigDecimal;

/**
 * Design Pattern: Abstract Factory - Product Interface
 * 
 * Defines the interface for payment notifications.
 * Each concrete notifier implements notification logic specific to its payment platform.
 */
public interface PaymentNotifier {
    void notifyPaymentSuccess(Order order, BigDecimal amount);
    void notifyPaymentFailure(Order order, BigDecimal amount, String reason);
    void notifyRefundProcessed(Order order, BigDecimal amount);
    String getNotifierName();
}

