package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Design Pattern: Abstract Factory - Concrete Product
 * 
 * Implements cash on delivery payment method (no upfront payment required).
 */
@Component
public class CashOnDeliveryPaymentProvider implements PaymentProvider {
    @Override
    public boolean authorizePayment(Order order, BigDecimal amount) {
        // COD doesn't require authorization
        System.out.println("Cash on Delivery: Order " + order.getId() + " will be paid upon delivery");
        return true;
    }

    @Override
    public boolean capturePayment(Order order, BigDecimal amount) {
        // COD payment is captured when delivered
        System.out.println("Cash on Delivery: Payment of " + amount + " received for order " + order.getId());
        return true;
    }

    @Override
    public boolean refund(Order order, BigDecimal amount) {
        // COD refunds are handled manually
        System.out.println("Cash on Delivery: Manual refund process initiated for order " + order.getId());
        return true;
    }

    @Override
    public String getProviderName() {
        return "Cash on Delivery";
    }
}

