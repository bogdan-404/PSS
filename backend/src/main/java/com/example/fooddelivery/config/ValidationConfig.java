package com.example.fooddelivery.config;

import com.example.fooddelivery.pattern.behavioral.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that sets up the Chain of Responsibility for order validation.
 */
@Configuration
public class ValidationConfig {
    @Bean
    public OrderValidationHandler validationChain(
            ItemsAvailabilityValidator itemsValidator,
            AddressValidator addressValidator,
            PaymentDetailsValidator paymentValidator,
            FraudCheckValidator fraudValidator) {
        // Build the chain: items -> address -> payment -> fraud
        itemsValidator.setNext(addressValidator)
                     .setNext(paymentValidator)
                     .setNext(fraudValidator);
        return itemsValidator;
    }
}

