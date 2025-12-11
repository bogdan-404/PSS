package com.example.fooddelivery.pattern.creational;

import org.springframework.stereotype.Component;

/**
 * Design Pattern: Abstract Factory - Concrete Factory
 * 
 * Creates all Stripe payment platform components.
 * This factory creates a family of related objects: PaymentProvider, PaymentValidator, and PaymentNotifier.
 */
@Component
public class StripePaymentPlatformFactory implements PaymentPlatformFactory {
    private final StripePaymentProvider paymentProvider;
    private final StripePaymentValidator paymentValidator;
    private final StripePaymentNotifier paymentNotifier;

    public StripePaymentPlatformFactory(
            StripePaymentProvider paymentProvider,
            StripePaymentValidator paymentValidator,
            StripePaymentNotifier paymentNotifier) {
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
        return "Stripe";
    }
}

