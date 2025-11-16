package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.domain.PaymentMethod;
import com.example.fooddelivery.dto.OrderCreationRequest;
import org.springframework.stereotype.Component;

/**
 * Design Pattern: Chain of Responsibility - Concrete Handler
 * 
 * Validates that payment method is specified for non-COD orders.
 */
@Component
public class PaymentDetailsValidator extends OrderValidationHandler {
    @Override
    protected void doValidate(OrderCreationRequest request) throws ValidationException {
        if (request.getPaymentMethod() == null) {
            throw new ValidationException("Payment method is required");
        }
        
        // For non-COD methods, we might want to validate payment details exist
        // For this demo, we just check that a method is selected
        if (request.getPaymentMethod() != PaymentMethod.CASH_ON_DELIVERY) {
            // In a real system, we'd validate payment details here
            // For now, we just ensure a method is selected
        }
    }
}

