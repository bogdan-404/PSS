package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.domain.Notification;
import com.example.fooddelivery.domain.OrderStatus;
import com.example.fooddelivery.repository.NotificationRepository;
import org.springframework.stereotype.Component;

/**
 * Design Pattern: Observer - Concrete Observer
 * 
 * Listens to order events and sends notifications to couriers.
 */
@Component
public class CourierNotificationListener implements OrderEventListener {
    private final NotificationRepository notificationRepository;

    public CourierNotificationListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void onOrderEvent(OrderEvent event) {
        if (event instanceof OrderStatusChangedEvent) {
            OrderStatusChangedEvent statusEvent = (OrderStatusChangedEvent) event;
            if (statusEvent.getNewStatus() == OrderStatus.ON_THE_WAY && event.getOrder().getCourier() != null) {
                Notification notification = new Notification(
                    "COURIER",
                    event.getOrder().getCourier().getId(),
                    "Order #" + event.getOrder().getId() + " is ready for delivery",
                    event.getOrder()
                );
                notificationRepository.save(notification);
                System.out.println("Courier notification: Order #" + event.getOrder().getId() + " ready for delivery");
            } else if (statusEvent.getNewStatus() == OrderStatus.DELIVERED && event.getOrder().getCourier() != null) {
                Notification notification = new Notification(
                    "COURIER",
                    event.getOrder().getCourier().getId(),
                    "Order #" + event.getOrder().getId() + " has been delivered successfully",
                    event.getOrder()
                );
                notificationRepository.save(notification);
                System.out.println("Courier notification: Order #" + event.getOrder().getId() + " delivered");
            }
        }
    }
}

