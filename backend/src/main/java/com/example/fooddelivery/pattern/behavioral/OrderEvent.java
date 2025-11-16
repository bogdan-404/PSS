package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.domain.Order;

/**
 * Design Pattern: Observer - Subject Event
 * 
 * Base class for order-related events that observers can listen to.
 */
public abstract class OrderEvent {
    private final Order order;
    private final long timestamp;

    public OrderEvent(Order order) {
        this.order = order;
        this.timestamp = System.currentTimeMillis();
    }

    public Order getOrder() {
        return order;
    }

    public long getTimestamp() {
        return timestamp;
    }
}

