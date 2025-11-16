package com.example.fooddelivery.dto;

import com.example.fooddelivery.domain.Order;
import com.example.fooddelivery.domain.OrderItem;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomer().getId());
        response.setCustomerName(order.getCustomer().getName());
        response.setRestaurantId(order.getRestaurant().getId());
        response.setRestaurantName(order.getRestaurant().getName());
        if (order.getCourier() != null) {
            response.setCourierId(order.getCourier().getId());
            response.setCourierName(order.getCourier().getName());
        }
        response.setType(order.getType());
        response.setStatus(order.getStatus());
        response.setTotalPrice(order.getTotalPrice());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setCreatedAt(order.getCreatedAt());
        response.setScheduledFor(order.getScheduledFor());
        response.setPriority(order.getPriority());
        response.setExtraPackaging(order.getExtraPackaging());
        response.setInsurance(order.getInsurance());
        
        response.setItems(order.getItems().stream()
            .map(this::toItemResponse)
            .collect(Collectors.toList()));
        
        if (order.getDeliveryRoute() != null) {
            response.setDeliveryRoute(toRouteResponse(order.getDeliveryRoute()));
        }
        
        return response;
    }

    private OrderResponse.OrderItemResponse toItemResponse(OrderItem item) {
        OrderResponse.OrderItemResponse response = new OrderResponse.OrderItemResponse();
        response.setId(item.getId());
        response.setMenuItemId(item.getMenuItem().getId());
        response.setMenuItemName(item.getMenuItem().getName());
        response.setQuantity(item.getQuantity());
        response.setFinalPrice(item.getFinalPrice());
        return response;
    }

    private OrderResponse.DeliveryRouteResponse toRouteResponse(com.example.fooddelivery.domain.DeliveryRoute route) {
        OrderResponse.DeliveryRouteResponse response = new OrderResponse.DeliveryRouteResponse();
        response.setId(route.getId());
        response.setDistanceKm(route.getDistanceKm());
        response.setEstimatedTimeMinutes(route.getEstimatedTimeMinutes());
        response.setRouteDetails(route.getRouteDetails());
        return response;
    }
}

