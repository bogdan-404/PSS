package com.example.fooddelivery.pattern.behavioral;

/**
 * Design Pattern: Observer - Observer Interface
 * 
 * Interface for objects that want to be notified of order events.
 */
public interface OrderEventListener {
    void onOrderEvent(OrderEvent event);
}

