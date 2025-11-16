package com.example.fooddelivery.pattern.structural;

import org.springframework.stereotype.Component;

/**
 * Design Pattern: Adapter - Concrete Implementation
 * 
 * Dummy adapter that simulates route calculation without external API calls.
 * In production, this could be replaced with GoogleMapsRouteAdapter or similar.
 */
@Component
public class DummyRouteClientAdapter implements RouteClient {
    @Override
    public RouteData getRoute(String origin, String destination) {
        // Simulated route calculation
        // In real implementation, this would call Google Maps API or similar
        double distance = Math.random() * 10 + 2; // Random distance between 2-12 km
        int timeMinutes = (int) (distance * 3 + Math.random() * 10); // Rough estimate
        
        String details = String.format(
            "Route from %s to %s: %.2f km, estimated %d minutes",
            origin, destination, distance, timeMinutes
        );
        
        return new RouteData(distance, timeMinutes, details);
    }
}

