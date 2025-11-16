package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design Pattern: Abstract Factory - Concrete Product
 * 
 * Implements payment processing using Stripe payment gateway.
 */
@Component
public class StripePaymentProvider implements PaymentProvider {
    @Override
    public boolean authorizePayment(Order order, BigDecimal amount) {
        // Simulated Stripe authorization
        System.out.println("Stripe: Authorizing payment of " + amount + " for order " + order.getId());
        return true;
    }

    @Override
    public boolean capturePayment(Order order, BigDecimal amount) {
        // Simulated Stripe capture
        System.out.println("Stripe: Capturing payment of " + amount + " for order " + order.getId());
        return true;
    }

    @Override
    public boolean refund(Order order, BigDecimal amount) {
        // Simulated Stripe refund
        System.out.println("Stripe: Refunding " + amount + " for order " + order.getId());
        return true;
    }

    @Override
    public String getProviderName() {
        return "Stripe";
    }
}

