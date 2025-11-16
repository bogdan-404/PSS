package com.example.fooddelivery.config;

import com.example.fooddelivery.pattern.behavioral.*;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

/**
 * Configuration class that registers all observers with the event publisher.
 */
@Configuration
public class ObserverConfig {
    private final OrderEventPublisher eventPublisher;
    private final CustomerNotificationListener customerListener;
    private final RestaurantNotificationListener restaurantListener;
    private final CourierNotificationListener courierListener;

    public ObserverConfig(
            OrderEventPublisher eventPublisher,
            CustomerNotificationListener customerListener,
            RestaurantNotificationListener restaurantListener,
            CourierNotificationListener courierListener) {
        this.eventPublisher = eventPublisher;
        this.customerListener = customerListener;
        this.restaurantListener = restaurantListener;
        this.courierListener = courierListener;
    }

    @PostConstruct
    public void registerObservers() {
        eventPublisher.addListener(customerListener);
        eventPublisher.addListener(restaurantListener);
        eventPublisher.addListener(courierListener);
    }
}

