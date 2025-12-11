package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Design Pattern: Abstract Factory - Factory Client
 * 
 * Client that uses Abstract Factory pattern to get the appropriate payment platform factory.
 * Each factory creates a family of related objects (PaymentProvider, PaymentValidator, PaymentNotifier).
 */
@Component
public class PaymentProviderFactory {
    private final Map<PaymentMethod, PaymentPlatformFactory> platformFactories;

    public PaymentProviderFactory(
            StripePaymentPlatformFactory stripeFactory,
            PaypalPaymentPlatformFactory paypalFactory,
            CashOnDeliveryPlatformFactory codFactory) {
        this.platformFactories = new HashMap<>();
        this.platformFactories.put(PaymentMethod.STRIPE, stripeFactory);
        this.platformFactories.put(PaymentMethod.PAYPAL, paypalFactory);
        this.platformFactories.put(PaymentMethod.CASH_ON_DELIVERY, codFactory);
    }

    public PaymentPlatformFactory getPlatformFactory(PaymentMethod paymentMethod) {
        PaymentPlatformFactory factory = platformFactories.get(paymentMethod);
        if (factory == null) {
            throw new IllegalArgumentException("No payment platform factory found for method: " + paymentMethod);
        }
        return factory;
    }

    /**
     * Convenience method to get payment provider (maintains backward compatibility)
     */
    public PaymentProvider getPaymentProvider(PaymentMethod paymentMethod) {
        return getPlatformFactory(paymentMethod).createPaymentProvider();
    }
}

