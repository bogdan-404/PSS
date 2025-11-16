package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.dto.OrderCreationRequest;
import org.springframework.stereotype.Component;

/**
 * Design Pattern: Chain of Responsibility - Concrete Handler
 * 
 * Performs a simple fraud check on the order.
 * In a real system, this would check against fraud databases, etc.
 */
@Component
public class FraudCheckValidator extends OrderValidationHandler {
    @Override
    protected void doValidate(OrderCreationRequest request) throws ValidationException {
        // Simple fraud check: reject orders with suspiciously high quantities
        if (request.getItems() != null) {
            for (OrderCreationRequest.OrderItemRequest item : request.getItems()) {
                if (item.getQuantity() > 50) {
                    throw new ValidationException(
                        "Fraud check failed: Quantity " + item.getQuantity() + 
                        " exceeds maximum allowed per item"
                    );
                }
            }
        }
        
        // Additional fraud checks could be added here
    }
}

