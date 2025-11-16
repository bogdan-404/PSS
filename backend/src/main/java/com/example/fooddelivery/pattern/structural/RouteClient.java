package com.example.fooddelivery.pattern.structural;

/**
 * Design Pattern: Adapter - Target Interface
 * 
 * Defines the interface for route calculation services.
 * This allows the system to work with different routing providers
 * without changing the business logic.
 */
public interface RouteClient {
    RouteData getRoute(String origin, String destination);
    
    class RouteData {
        private Double distanceKm;
        private Integer estimatedTimeMinutes;
        private String routeDetails;

        public RouteData(Double distanceKm, Integer estimatedTimeMinutes, String routeDetails) {
            this.distanceKm = distanceKm;
            this.estimatedTimeMinutes = estimatedTimeMinutes;
            this.routeDetails = routeDetails;
        }

        public Double getDistanceKm() {
            return distanceKm;
        }

        public Integer getEstimatedTimeMinutes() {
            return estimatedTimeMinutes;
        }

        public String getRouteDetails() {
            return routeDetails;
        }
    }
}

