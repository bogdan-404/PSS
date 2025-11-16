package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.domain.MenuItem;
import com.example.fooddelivery.dto.OrderCreationRequest;
import com.example.fooddelivery.repository.MenuItemRepository;
import org.springframework.stereotype.Component;

/**
 * Design Pattern: Chain of Responsibility - Concrete Handler
 * 
 * Validates that all requested menu items are available in stock.
 */
@Component
public class ItemsAvailabilityValidator extends OrderValidationHandler {
    private final MenuItemRepository menuItemRepository;

    public ItemsAvailabilityValidator(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    protected void doValidate(OrderCreationRequest request) throws ValidationException {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new ValidationException("Order must contain at least one item");
        }

        for (OrderCreationRequest.OrderItemRequest itemRequest : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                .orElseThrow(() -> new ValidationException("MenuItem not found: " + itemRequest.getMenuItemId()));
            
            if (menuItem.getStock() < itemRequest.getQuantity()) {
                throw new ValidationException(
                    "Insufficient stock for " + menuItem.getName() + 
                    ". Available: " + menuItem.getStock() + ", Requested: " + itemRequest.getQuantity()
                );
            }
        }
    }
}

