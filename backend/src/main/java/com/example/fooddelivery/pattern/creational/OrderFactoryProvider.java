package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.OrderType;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Helper class to provide the appropriate factory based on order type.
 * Used by the Facade to get the correct Factory Method implementation.
 */
@Component
public class OrderFactoryProvider {
    private final Map<OrderType, OrderFactory> factories;

    public OrderFactoryProvider(
            StandardOrderFactory standardFactory,
            ScheduledOrderFactory scheduledFactory,
            GroupOrderFactory groupFactory) {
        this.factories = new HashMap<>();
        this.factories.put(OrderType.STANDARD, standardFactory);
        this.factories.put(OrderType.SCHEDULED, scheduledFactory);
        this.factories.put(OrderType.GROUP, groupFactory);
    }

    public OrderFactory getFactory(OrderType orderType) {
        OrderFactory factory = factories.get(orderType);
        if (factory == null) {
            throw new IllegalArgumentException("No factory found for order type: " + orderType);
        }
        return factory;
    }
}

