package com.example.fooddelivery.dto;

import com.example.fooddelivery.domain.OrderStatus;
import com.example.fooddelivery.domain.OrderType;
import com.example.fooddelivery.domain.PaymentMethod;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private Long customerId;
    private String customerName;
    private Long restaurantId;
    private String restaurantName;
    private Long courierId;
    private String courierName;
    private OrderType type;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime scheduledFor;
    private Boolean priority;
    private Boolean extraPackaging;
    private Boolean insurance;
    private List<OrderItemResponse> items;
    private DeliveryRouteResponse deliveryRoute;

    public OrderResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getScheduledFor() {
        return scheduledFor;
    }

    public void setScheduledFor(LocalDateTime scheduledFor) {
        this.scheduledFor = scheduledFor;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public Boolean getExtraPackaging() {
        return extraPackaging;
    }

    public void setExtraPackaging(Boolean extraPackaging) {
        this.extraPackaging = extraPackaging;
    }

    public Boolean getInsurance() {
        return insurance;
    }

    public void setInsurance(Boolean insurance) {
        this.insurance = insurance;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public DeliveryRouteResponse getDeliveryRoute() {
        return deliveryRoute;
    }

    public void setDeliveryRoute(DeliveryRouteResponse deliveryRoute) {
        this.deliveryRoute = deliveryRoute;
    }

    public static class OrderItemResponse {
        private Long id;
        private Long menuItemId;
        private String menuItemName;
        private Integer quantity;
        private BigDecimal finalPrice;

        public OrderItemResponse() {}

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getMenuItemId() {
            return menuItemId;
        }

        public void setMenuItemId(Long menuItemId) {
            this.menuItemId = menuItemId;
        }

        public String getMenuItemName() {
            return menuItemName;
        }

        public void setMenuItemName(String menuItemName) {
            this.menuItemName = menuItemName;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getFinalPrice() {
            return finalPrice;
        }

        public void setFinalPrice(BigDecimal finalPrice) {
            this.finalPrice = finalPrice;
        }
    }

    public static class DeliveryRouteResponse {
        private Long id;
        private Double distanceKm;
        private Integer estimatedTimeMinutes;
        private String routeDetails;

        public DeliveryRouteResponse() {}

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Double getDistanceKm() {
            return distanceKm;
        }

        public void setDistanceKm(Double distanceKm) {
            this.distanceKm = distanceKm;
        }

        public Integer getEstimatedTimeMinutes() {
            return estimatedTimeMinutes;
        }

        public void setEstimatedTimeMinutes(Integer estimatedTimeMinutes) {
            this.estimatedTimeMinutes = estimatedTimeMinutes;
        }

        public String getRouteDetails() {
            return routeDetails;
        }

        public void setRouteDetails(String routeDetails) {
            this.routeDetails = routeDetails;
        }
    }
}

