package com.example.fooddelivery.controller;

import com.example.fooddelivery.domain.Notification;
import com.example.fooddelivery.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {
    private final NotificationRepository notificationRepository;

    public NotificationController(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDto>> getNotifications(
            @RequestParam(required = false) String recipientType,
            @RequestParam(required = false) Long recipientId) {
        List<Notification> notifications;
        if (recipientType != null && recipientId != null) {
            notifications = notificationRepository.findByRecipientTypeAndRecipientId(recipientType, recipientId);
        } else {
            notifications = notificationRepository.findAll();
        }
        
        List<NotificationDto> dtos = notifications.stream()
            .map(this::toDto)
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private NotificationDto toDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setId(notification.getId());
        dto.setRecipientType(notification.getRecipientType());
        dto.setRecipientId(notification.getRecipientId());
        dto.setMessage(notification.getMessage());
        dto.setCreatedAt(notification.getCreatedAt());
        if (notification.getOrder() != null) {
            dto.setOrderId(notification.getOrder().getId());
        }
        return dto;
    }

    public static class NotificationDto {
        private Long id;
        private String recipientType;
        private Long recipientId;
        private String message;
        private java.time.LocalDateTime createdAt;
        private Long orderId;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getRecipientType() {
            return recipientType;
        }

        public void setRecipientType(String recipientType) {
            this.recipientType = recipientType;
        }

        public Long getRecipientId() {
            return recipientId;
        }

        public void setRecipientId(Long recipientId) {
            this.recipientId = recipientId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public java.time.LocalDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(java.time.LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }
    }
}

