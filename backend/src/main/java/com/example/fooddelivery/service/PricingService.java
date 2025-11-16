package com.example.fooddelivery.service;

import com.example.fooddelivery.domain.Order;
import com.example.fooddelivery.pattern.behavioral.*;
import com.example.fooddelivery.pattern.structural.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Service that applies pricing strategies and decorators to calculate final order price.
 * Demonstrates Strategy and Decorator patterns working together.
 */
@Service
public class PricingService {
    private final BasePriceCalculator basePriceCalculator;
    private final List<PricingStrategy> strategies;

    public PricingService(
            BasePriceCalculator basePriceCalculator,
            DistanceBasedPricingStrategy distanceStrategy,
            PeakHourPricingStrategy peakHourStrategy,
            PromotionalPricingStrategy promotionalStrategy) {
        this.basePriceCalculator = basePriceCalculator;
        this.strategies = Arrays.asList(
            distanceStrategy,
            peakHourStrategy,
            promotionalStrategy
        );
    }

    public BigDecimal calculateFinalPrice(Order order) {
        // Start with base price
        PriceCalculator calculator = basePriceCalculator;
        BigDecimal basePrice = calculator.calculatePrice(order);

        // Apply strategies
        BigDecimal priceAfterStrategies = basePrice;
        for (PricingStrategy strategy : strategies) {
            priceAfterStrategies = strategy.apply(priceAfterStrategies, order);
        }

        // Update order items' final prices for decorator calculation
        // (In a real system, we'd recalculate with the strategy-adjusted base)
        order.getItems().forEach(item -> {
            item.setFinalPrice(item.getMenuItem().getBasePrice());
        });

        // Apply decorators (priority, packaging, insurance)
        calculator = basePriceCalculator;
        if (order.isPriority()) {
            calculator = new PriorityDeliveryPriceDecorator(calculator);
        }
        if (Boolean.TRUE.equals(order.getExtraPackaging())) {
            calculator = new ExtraPackagingPriceDecorator(calculator);
        }
        if (Boolean.TRUE.equals(order.getInsurance())) {
            calculator = new InsurancePriceDecorator(calculator);
        }

        BigDecimal decoratorPrice = calculator.calculatePrice(order);

        // Combine strategy adjustments with decorator additions
        BigDecimal strategyAdjustment = priceAfterStrategies.subtract(basePrice);
        return decoratorPrice.add(strategyAdjustment);
    }
}

