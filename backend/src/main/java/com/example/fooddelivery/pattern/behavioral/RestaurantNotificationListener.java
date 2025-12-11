package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.domain.Notification;
import com.example.fooddelivery.domain.OrderStatus;
import com.example.fooddelivery.repository.NotificationRepository;
import org.springframework.stereotype.Component;

/**
 * Design Pattern: Observer - Concrete Observer
 * 
 * Listens to order events and sends notifications to restaurants.
 */
@Component
public class RestaurantNotificationListener implements OrderEventListener {
    private final NotificationRepository notificationRepository;

    public RestaurantNotificationListener(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void onOrderEvent(OrderEvent event) {
        if (event instanceof OrderCreatedEvent) {
            // Add null check for restaurant to avoid NullPointerException
            if (event.getOrder().getRestaurant() != null) {
                Notification notification = new Notification(
                    "RESTAURANT",
                    event.getOrder().getRestaurant().getId(),
                    "New order #" + event.getOrder().getId() + " received!",
                    event.getOrder()
                );
                notificationRepository.save(notification);
                System.out.println("Restaurant notification: New order #" + event.getOrder().getId());
            } else {
                System.err.println("Warning: Cannot create restaurant notification - restaurant is null for order #" + event.getOrder().getId());
            }
        } else if (event instanceof OrderStatusChangedEvent) {
            OrderStatusChangedEvent statusEvent = (OrderStatusChangedEvent) event;
            if (statusEvent.getNewStatus() == OrderStatus.PREPARING) {
                // Add null check for restaurant to avoid NullPointerException
                if (event.getOrder().getRestaurant() != null) {
                    Notification notification = new Notification(
                        "RESTAURANT",
                        event.getOrder().getRestaurant().getId(),
                        "Order #" + event.getOrder().getId() + " is now being prepared",
                        event.getOrder()
                    );
                    notificationRepository.save(notification);
                    System.out.println("Restaurant notification: Order #" + event.getOrder().getId() + " preparing");
                } else {
                    System.err.println("Warning: Cannot create restaurant notification - restaurant is null for order #" + event.getOrder().getId());
                }
            }
        }
    }
}

