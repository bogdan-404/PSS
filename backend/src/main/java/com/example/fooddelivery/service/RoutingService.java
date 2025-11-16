package com.example.fooddelivery.service;

import com.example.fooddelivery.domain.DeliveryRoute;
import com.example.fooddelivery.domain.Order;
import com.example.fooddelivery.pattern.creational.DeliveryRouteBuilder;
import com.example.fooddelivery.pattern.structural.RouteClient;
import org.springframework.stereotype.Service;

/**
 * Service that uses Adapter pattern to get route data and Builder pattern to create DeliveryRoute.
 */
@Service
public class RoutingService {
    private final RouteClient routeClient;

    public RoutingService(RouteClient routeClient) {
        this.routeClient = routeClient;
    }

    public DeliveryRoute createRoute(Order order) {
        String origin = order.getRestaurant().getAddress();
        String destination = order.getCustomer().getAddress();

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

