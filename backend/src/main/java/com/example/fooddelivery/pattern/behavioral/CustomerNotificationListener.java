package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.domain.Notification;
import com.example.fooddelivery.domain.OrderStatus;
import com.example.fooddelivery.repository.NotificationRepository;
import org.springframework.stereotype.Component;

/**
 * Design Pattern: Observer - Concrete Observer
 * 
 * Listens to order events and sends notifications to customers.
 */
@Component
public class CustomerNotificationListener implements OrderEventListener {
    private final NotificationRepository notificationRepository;

    public CustomerNotificationListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void onOrderEvent(OrderEvent event) {
        if (event instanceof OrderCreatedEvent) {
            Notification notification = new Notification(
                "CUSTOMER",
                event.getOrder().getCustomer().getId(),
                "Your order #" + event.getOrder().getId() + " has been created successfully!",
                event.getOrder()
            );
            notificationRepository.save(notification);
            System.out.println("Customer notification: Order #" + event.getOrder().getId() + " created");
        } else if (event instanceof OrderStatusChangedEvent) {
            OrderStatusChangedEvent statusEvent = (OrderStatusChangedEvent) event;
            String message = String.format(
                "Your order #%d status changed from %s to %s",
                event.getOrder().getId(),
                statusEvent.getOldStatus(),
                statusEvent.getNewStatus()
            );
            Notification notification = new Notification(
                "CUSTOMER",
                event.getOrder().getCustomer().getId(),
                message,
                event.getOrder()
            );
            notificationRepository.save(notification);
            System.out.println("Customer notification: " + message);
        }
    }
}

