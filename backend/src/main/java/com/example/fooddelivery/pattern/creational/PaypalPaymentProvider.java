package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design Pattern: Abstract Factory - Concrete Product
 * 
 * Implements payment processing using PayPal payment gateway.
 */
@Component
public class PaypalPaymentProvider implements PaymentProvider {
    @Override
    public boolean authorizePayment(Order order, BigDecimal amount) {
        // Simulated PayPal authorization
        System.out.println("PayPal: Authorizing payment of " + amount + " for order " + order.getId());
        return true;
    }

    @Override
    public boolean capturePayment(Order order, BigDecimal amount) {
        // Simulated PayPal capture
        System.out.println("PayPal: Capturing payment of " + amount + " for order " + order.getId());
        return true;
    }

    @Override
    public boolean refund(Order order, BigDecimal amount) {
        // Simulated PayPal refund
        System.out.println("PayPal: Refunding " + amount + " for order " + order.getId());
        return true;
    }

    @Override
    public String getProviderName() {
        return "PayPal";
    }
}

