package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.domain.Order;

/**
 * Design Pattern: Observer - Concrete Event
 * 
 * Event fired when a new order is created.
 */
public class OrderCreatedEvent extends OrderEvent {
    public OrderCreatedEvent(Order order) {
        super(order);
    }
}

