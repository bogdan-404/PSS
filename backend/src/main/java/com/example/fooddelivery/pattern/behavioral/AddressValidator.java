package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.domain.Customer;
import com.example.fooddelivery.dto.OrderCreationRequest;
import com.example.fooddelivery.repository.CustomerRepository;
import org.springframework.stereotype.Component;

/**
 * Design Pattern: Chain of Responsibility - Concrete Handler
 * 
 * Validates that the customer has a valid delivery address.
 */
@Component
public class AddressValidator extends OrderValidationHandler {
    private final CustomerRepository customerRepository;

    public AddressValidator(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    protected void doValidate(OrderCreationRequest request) throws ValidationException {
        Customer customer = customerRepository.findById(request.getCustomerId())
            .orElseThrow(() -> new ValidationException("Customer not found: " + request.getCustomerId()));
        
        if (customer.getAddress() == null || customer.getAddress().trim().isEmpty()) {
            throw new ValidationException("Customer address is required for delivery");
        }
    }
}

