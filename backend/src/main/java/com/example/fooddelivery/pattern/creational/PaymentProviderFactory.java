package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Design Pattern: Abstract Factory
 * 
 * Factory that creates appropriate payment provider instances based on payment method.
 * This encapsulates the creation logic and allows easy extension with new payment providers.
 */
@Component
public class PaymentProviderFactory {
    private final Map<PaymentMethod, PaymentProvider> providers;

    public PaymentProviderFactory(
            StripePaymentProvider stripeProvider,
            PaypalPaymentProvider paypalProvider,
            CashOnDeliveryPaymentProvider codProvider) {
        this.providers = new HashMap<>();
        this.providers.put(PaymentMethod.STRIPE, stripeProvider);
        this.providers.put(PaymentMethod.PAYPAL, paypalProvider);
        this.providers.put(PaymentMethod.CASH_ON_DELIVERY, codProvider);
    }

    public PaymentProvider getPaymentProvider(PaymentMethod paymentMethod) {
        PaymentProvider provider = providers.get(paymentMethod);
        if (provider == null) {
            throw new IllegalArgumentException("No payment provider found for method: " + paymentMethod);
        }
        return provider;
    }
}

