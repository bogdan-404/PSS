package com.example.fooddelivery.pattern.creational;

import com.example.fooddelivery.domain.DeliveryRoute;
import com.example.fooddelivery.domain.Order;

/**
 * Design Pattern: Builder
 * 
 * Provides a fluent interface for constructing DeliveryRoute objects step by step.
 * This allows for flexible and readable route creation.
 */
public class DeliveryRouteBuilder {
    private Order order;
    private Double distanceKm;
    private Integer estimatedTimeMinutes;
    private String routeDetails;

    public DeliveryRouteBuilder withOrder(Order order) {
        this.order = order;
        return this;
    }

    public DeliveryRouteBuilder withDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
        return this;
    }

    public DeliveryRouteBuilder withEstimatedTimeMinutes(Integer estimatedTimeMinutes) {
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        return this;
    }

    public DeliveryRouteBuilder withRouteDetails(String routeDetails) {
        this.routeDetails = routeDetails;
        return this;
    }

    public DeliveryRoute build() {
        if (order == null || distanceKm == null || estimatedTimeMinutes == null) {
            throw new IllegalStateException("Order, distance, and estimated time are required");
        }
        DeliveryRoute route = new DeliveryRoute(order, distanceKm, estimatedTimeMinutes, routeDetails);
        order.setDeliveryRoute(route);
        return route;
    }
}

