package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design Pattern: Abstract Factory - Concrete Product
 * 
 * Implements payment notifications for PayPal platform.
 */
@Component
public class PaypalPaymentNotifier implements PaymentNotifier {
    @Override
    public void notifyPaymentSuccess(Order order, BigDecimal amount) {
        // PayPal-specific notification
        System.out.println("PayPal: Payment successful notification sent for order " + order.getId() + ", amount: " + amount);
    }

    @Override
    public void notifyPaymentFailure(Order order, BigDecimal amount, String reason) {
        // PayPal-specific failure notification
        System.out.println("PayPal: Payment failed notification sent for order " + order.getId() + ", reason: " + reason);
    }

    @Override
    public void notifyRefundProcessed(Order order, BigDecimal amount) {
        // PayPal-specific refund notification
        System.out.println("PayPal: Refund processed notification sent for order " + order.getId() + ", amount: " + amount);
    }

    @Override
    public String getNotifierName() {
        return "PayPal Payment Notifier";
    }
}

