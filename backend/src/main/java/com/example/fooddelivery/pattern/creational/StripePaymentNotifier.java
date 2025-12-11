package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design Pattern: Abstract Factory - Concrete Product
 * 
 * Implements payment notifications for Stripe platform.
 */
@Component
public class StripePaymentNotifier implements PaymentNotifier {
    @Override
    public void notifyPaymentSuccess(Order order, BigDecimal amount) {
        // Stripe-specific notification: send email, webhook, etc.
        System.out.println("Stripe: Payment successful notification sent for order " + order.getId() + ", amount: " + amount);
    }

    @Override
    public void notifyPaymentFailure(Order order, BigDecimal amount, String reason) {
        // Stripe-specific failure notification
        System.out.println("Stripe: Payment failed notification sent for order " + order.getId() + ", reason: " + reason);
    }

    @Override
    public void notifyRefundProcessed(Order order, BigDecimal amount) {
        // Stripe-specific refund notification
        System.out.println("Stripe: Refund processed notification sent for order " + order.getId() + ", amount: " + amount);
    }

    @Override
    public String getNotifierName() {
        return "Stripe Payment Notifier";
    }
}

