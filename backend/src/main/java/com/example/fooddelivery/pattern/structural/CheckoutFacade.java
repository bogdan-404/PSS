package com.example.fooddelivery.pattern.structural;

import com.example.fooddelivery.domain.*;
import com.example.fooddelivery.dto.OrderCreationRequest;
import com.example.fooddelivery.pattern.behavioral.*;
import com.example.fooddelivery.pattern.creational.*;
import com.example.fooddelivery.repository.*;
import com.example.fooddelivery.service.PricingService;
import com.example.fooddelivery.service.RoutingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Design Pattern: Facade
 * 
 * Provides a simplified interface to the complex subsystem of order processing.
 * Orchestrates validation, pricing, payment, routing, and notification processes.
 * This facade hides the complexity of the underlying system from clients.
 */
@Service
public class CheckoutFacade {
    private final OrderValidationHandler validationChain;
    private final OrderFactoryProvider orderFactoryProvider;
    private final PricingService pricingService;
    private final PaymentProviderFactory paymentProviderFactory;
    private final RoutingService routingService;
    private final OrderEventPublisher eventPublisher;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final CourierRepository courierRepository;

    public CheckoutFacade(
            OrderValidationHandler validationChain,
            OrderFactoryProvider orderFactoryProvider,
            PricingService pricingService,
            PaymentProviderFactory paymentProviderFactory,
            RoutingService routingService,
            OrderEventPublisher eventPublisher,
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository,
            CourierRepository courierRepository) {
        this.validationChain = validationChain;
        this.orderFactoryProvider = orderFactoryProvider;
        this.pricingService = pricingService;
        this.paymentProviderFactory = paymentProviderFactory;
        this.routingService = routingService;
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.courierRepository = courierRepository;
    }

    @Transactional
    public Order placeOrder(OrderCreationRequest request) throws OrderValidationHandler.ValidationException {
        // 1. Validate order (Chain of Responsibility)
        validationChain.validate(request);

        // 2. Load entities
        Customer customer = customerRepository.findById(request.getCustomerId())
            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
            .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        // 3. Create order using Factory Method
        OrderFactory factory = orderFactoryProvider.getFactory(request.getOrderType());
        Order order = factory.createOrder(request);
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);

        // 4. Add order items
        for (OrderCreationRequest.OrderItemRequest itemRequest : request.getItems()) {
            MenuItem menuItem = menuItemRepository.findById(itemRequest.getMenuItemId())
                .orElseThrow(() -> new IllegalArgumentException("MenuItem not found"));
            
            BigDecimal itemPrice = menuItem.getBasePrice();
            OrderItem orderItem = new OrderItem(order, menuItem, itemRequest.getQuantity(), itemPrice);
            order.getItems().add(orderItem);
            
            // Update stock
            menuItem.setStock(menuItem.getStock() - itemRequest.getQuantity());
            menuItemRepository.save(menuItem);
        }

        // 5. Calculate route (Adapter + Builder)
        DeliveryRoute route = routingService.createRoute(order);
        order.setDeliveryRoute(route);

        // 6. Calculate price (Strategy + Decorator)
        BigDecimal totalPrice = pricingService.calculateFinalPrice(order);
        order.setTotalPrice(totalPrice);

        // 7. Process payment (Abstract Factory)
        PaymentProvider paymentProvider = paymentProviderFactory.getPaymentProvider(request.getPaymentMethod());
        boolean paymentAuthorized = paymentProvider.authorizePayment(order, totalPrice);
        if (!paymentAuthorized) {
            throw new IllegalStateException("Payment authorization failed");
        }

        // 8. Assign courier (simple assignment for demo)
        List<Courier> couriers = courierRepository.findAll();
        if (!couriers.isEmpty()) {
            order.setCourier(couriers.get(0));
        }

        // 9. Save order
        order.setStatus(OrderStatus.CONFIRMED);
        Order savedOrder = orderRepository.save(order);

        // 10. Publish event (Observer)
        eventPublisher.publish(new OrderCreatedEvent(savedOrder));

        return savedOrder;
    }
}

