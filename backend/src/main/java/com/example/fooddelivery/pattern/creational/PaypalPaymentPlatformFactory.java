package com.example.fooddelivery.pattern.creational;

import org.springframework.stereotype.Component;

/**
 * Design Pattern: Abstract Factory - Concrete Factory
 * 
 * Creates all PayPal payment platform components.
 * This factory creates a family of related objects: PaymentProvider, PaymentValidator, and PaymentNotifier.
 */
@Component
public class PaypalPaymentPlatformFactory implements PaymentPlatformFactory {
    private final PaypalPaymentProvider paymentProvider;
    private final PaypalPaymentValidator paymentValidator;
    private final PaypalPaymentNotifier paymentNotifier;

    public PaypalPaymentPlatformFactory(
            PaypalPaymentProvider paymentProvider,
            PaypalPaymentValidator paymentValidator,
            PaypalPaymentNotifier paymentNotifier) {
        this.paymentProvider = paymentProvider;
        this.paymentValidator = paymentValidator;
        this.paymentNotifier = paymentNotifier;
    }

    @Override
    public PaymentProvider createPaymentProvider() {
        return paymentProvider;
    }

    @Override
    public PaymentValidator createPaymentValidator() {
        return paymentValidator;
    }

    @Override
    public PaymentNotifier createPaymentNotifier() {
        return paymentNotifier;
    }

    @Override
    public String getPlatformName() {
        return "PayPal";
    }
}

