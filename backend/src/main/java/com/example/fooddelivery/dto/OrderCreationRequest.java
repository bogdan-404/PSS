package com.example.fooddelivery.dto;

import com.example.fooddelivery.domain.OrderType;
import com.example.fooddelivery.domain.PaymentMethod;
import java.time.LocalDateTime;
import java.util.List;

public class OrderCreationRequest {
    private Long customerId;
    private Long restaurantId;
    private OrderType orderType;
    private List<OrderItemRequest> items;
    private PaymentMethod paymentMethod;
    private LocalDateTime scheduledFor;
    private Boolean priority = false;
    private Boolean extraPackaging = false;
    private Boolean insurance = false;

    public OrderCreationRequest() {}

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public static class OrderItemRequest {
        private Long menuItemId;
        private Integer quantity;

        public OrderItemRequest() {}

        public OrderItemRequest(Long menuItemId, Integer quantity) {
            this.menuItemId = menuItemId;
            this.quantity = quantity;
        }

        public Long getMenuItemId() {
            return menuItemId;
        }

        public void setMenuItemId(Long menuItemId) {
            this.menuItemId = menuItemId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}

