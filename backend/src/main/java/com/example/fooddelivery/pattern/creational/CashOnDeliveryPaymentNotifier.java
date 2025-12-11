package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design Pattern: Abstract Factory - Concrete Product
 * 
 * Implements payment notifications for Cash on Delivery platform.
 */
@Component
public class CashOnDeliveryPaymentNotifier implements PaymentNotifier {
    @Override
    public void notifyPaymentSuccess(Order order, BigDecimal amount) {
        // COD-specific notification: notify customer that payment will be collected on delivery
        System.out.println("Cash on Delivery: Payment will be collected on delivery for order " + order.getId() + ", amount: " + amount);
    }

    @Override
    public void notifyPaymentFailure(Order order, BigDecimal amount, String reason) {
        // COD doesn't have payment failures in the traditional sense
        System.out.println("Cash on Delivery: Order " + order.getId() + " - payment will be collected on delivery");
    }

    @Override
    public void notifyRefundProcessed(Order order, BigDecimal amount) {
        // COD refunds are rare, but if needed
        System.out.println("Cash on Delivery: Refund processed for order " + order.getId() + ", amount: " + amount);
    }

    @Override
    public String getNotifierName() {
        return "Cash on Delivery Payment Notifier";
    }
}

