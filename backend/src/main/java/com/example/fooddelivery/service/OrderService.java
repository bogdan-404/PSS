package com.example.fooddelivery.service;

import com.example.fooddelivery.domain.Order;
import com.example.fooddelivery.domain.OrderStatus;
import com.example.fooddelivery.pattern.behavioral.OrderEventPublisher;
import com.example.fooddelivery.pattern.behavioral.OrderStatusChangedEvent;
import com.example.fooddelivery.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for managing orders and triggering observer notifications on status changes.
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository, OrderEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = getOrder(orderId);
        OrderStatus oldStatus = order.getStatus();
        order.setStatus(newStatus);
        Order savedOrder = orderRepository.save(order);

        // Publish event (Observer pattern)
        eventPublisher.publish(new OrderStatusChangedEvent(savedOrder, oldStatus, newStatus));

        return savedOrder;
    }
}

