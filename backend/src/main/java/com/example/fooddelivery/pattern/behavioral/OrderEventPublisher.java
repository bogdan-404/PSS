package com.example.fooddelivery.pattern.behavioral;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Design Pattern: Observer - Subject
 * 
 * Publishes order events to all registered listeners.
 * Maintains a list of observers and notifies them when events occur.
 */
@Component
public class OrderEventPublisher {
    private final List<OrderEventListener> listeners = new ArrayList<>();

    public void addListener(OrderEventListener listener) {
        listeners.add(listener);
    }

    public void removeListener(OrderEventListener listener) {
        listeners.remove(listener);
    }

    public void publish(OrderEvent event) {
        for (OrderEventListener listener : listeners) {
            listener.onOrderEvent(event);
        }
    }
}

