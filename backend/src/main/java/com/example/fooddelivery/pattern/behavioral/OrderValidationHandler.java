package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.dto.OrderCreationRequest;

/**
 * Design Pattern: Chain of Responsibility
 * 
 * Abstract handler in the validation chain.
 * Each concrete handler validates a specific aspect of the order request.
 * If validation passes, the request is passed to the next handler.
 */
public abstract class OrderValidationHandler {
    protected OrderValidationHandler next;

    public OrderValidationHandler setNext(OrderValidationHandler next) {
        this.next = next;
        return next;
    }

    public void validate(OrderCreationRequest request) throws ValidationException {
        doValidate(request);
        if (next != null) {
            next.validate(request);
        }
    }

    protected abstract void doValidate(OrderCreationRequest request) throws ValidationException;

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}

