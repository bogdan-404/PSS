package com.example.fooddelivery.service;

import com.example.fooddelivery.domain.Customer;
import com.example.fooddelivery.domain.DeliveryRoute;
import com.example.fooddelivery.domain.Order;
import com.example.fooddelivery.domain.Restaurant;
import com.example.fooddelivery.pattern.creational.DeliveryRouteBuilder;
import com.example.fooddelivery.pattern.structural.RouteClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service that uses Adapter pattern to get route data and Builder pattern to create DeliveryRoute.
 */
@Service
@Transactional
public class RoutingService {
    private final RouteClient routeClient;

    public RoutingService(RouteClient routeClient) {
        this.routeClient = routeClient;
    }

    public DeliveryRoute createRoute(Order order, Restaurant restaurant, Customer customer) {
        // Be defensive: prefer explicitly provided entities, otherwise fall back to order relations
        Restaurant effectiveRestaurant = restaurant != null ? restaurant : order.getRestaurant();
        Customer effectiveCustomer = customer != null ? customer : order.getCustomer();

        if (effectiveRestaurant == null) {
            throw new IllegalStateException("Restaurant is null when creating route");
        }
        if (effectiveCustomer == null) {
            throw new IllegalStateException("Customer is null when creating route");
        }

        // Make sure the order holds the same references for later usage
        if (order.getRestaurant() == null) {
            order.setRestaurant(effectiveRestaurant);
        }
        if (order.getCustomer() == null) {
            order.setCustomer(effectiveCustomer);
        }

        String origin = effectiveRestaurant.getAddress();
        String destination = effectiveCustomer.getAddress();
        
        if (origin == null || origin.isEmpty()) {
            throw new IllegalStateException("Restaurant address is null or empty. Restaurant ID: " + restaurant.getId());
        }
        if (destination == null || destination.isEmpty()) {
            throw new IllegalStateException("Customer address is null or empty. Customer ID: " + customer.getId());
        }

        // Use Adapter to get route data
        RouteClient.RouteData routeData = routeClient.getRoute(origin, destination);

        // Use Builder to construct DeliveryRoute
        return new DeliveryRouteBuilder()
            .withOrder(order)
            .withDistanceKm(routeData.getDistanceKm())
            .withEstimatedTimeMinutes(routeData.getEstimatedTimeMinutes())
            .withRouteDetails(routeData.getRouteDetails())
            .build();
    }
}

