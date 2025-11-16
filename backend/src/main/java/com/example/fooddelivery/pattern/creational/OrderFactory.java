package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.Order;
import com.example.fooddelivery.dto.OrderCreationRequest;

/**
 * Design Pattern: Factory Method
 * 
 * Abstract factory interface for creating different types of orders.
 * Each concrete factory implements specific order creation logic.
 */
public interface OrderFactory {
    Order createOrder(OrderCreationRequest request);
}

