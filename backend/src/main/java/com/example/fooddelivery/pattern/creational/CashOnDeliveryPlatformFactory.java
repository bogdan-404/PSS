package com.example.fooddelivery.pattern.creational;

import org.springframework.stereotype.Component;

/**
 * Design Pattern: Abstract Factory - Concrete Factory
 * 
 * Creates all Cash on Delivery payment platform components.
 * This factory creates a family of related objects: PaymentProvider, PaymentValidator, and PaymentNotifier.
 */
@Component
public class CashOnDeliveryPlatformFactory implements PaymentPlatformFactory {
    private final CashOnDeliveryPaymentProvider paymentProvider;
    private final CashOnDeliveryPaymentValidator paymentValidator;
    private final CashOnDeliveryPaymentNotifier paymentNotifier;

    public CashOnDeliveryPlatformFactory(
            CashOnDeliveryPaymentProvider paymentProvider,
            CashOnDeliveryPaymentValidator paymentValidator,
            CashOnDeliveryPaymentNotifier paymentNotifier) {
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
        return "Cash on Delivery";
    }
}

