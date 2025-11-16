package com.example.fooddelivery.controller;

import com.example.fooddelivery.domain.Order;
import com.example.fooddelivery.domain.OrderStatus;
import com.example.fooddelivery.dto.OrderCreationRequest;
import com.example.fooddelivery.dto.OrderMapper;
import com.example.fooddelivery.dto.OrderResponse;
import com.example.fooddelivery.pattern.behavioral.OrderValidationHandler;
import com.example.fooddelivery.pattern.structural.CheckoutFacade;
import com.example.fooddelivery.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    private final CheckoutFacade checkoutFacade;
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(CheckoutFacade checkoutFacade, OrderService orderService, OrderMapper orderMapper) {
        this.checkoutFacade = checkoutFacade;
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderCreationRequest request) {
        try {
            Order order = checkoutFacade.placeOrder(request);
            OrderResponse response = orderMapper.toResponse(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (OrderValidationHandler.ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Failed to create order: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id);
        OrderResponse response = orderMapper.toResponse(order);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequest request) {
        OrderStatus newStatus = OrderStatus.valueOf(request.getStatus());
        Order order = orderService.updateOrderStatus(id, newStatus);
        OrderResponse response = orderMapper.toResponse(order);
        return ResponseEntity.ok(response);
    }

    public static class StatusUpdateRequest {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}

