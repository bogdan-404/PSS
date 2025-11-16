package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.domain.Order;
import com.example.fooddelivery.domain.OrderStatus;

/**
 * Design Pattern: Observer - Concrete Event
 * 
 * Event fired when an order's status changes.
 */
public class OrderStatusChangedEvent extends OrderEvent {
    private final OrderStatus oldStatus;
    private final OrderStatus newStatus;

    public OrderStatusChangedEvent(Order order, OrderStatus oldStatus, OrderStatus newStatus) {
        super(order);
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    public OrderStatus getOldStatus() {
        return oldStatus;
    }

    public OrderStatus getNewStatus() {
        return newStatus;
    }
}

