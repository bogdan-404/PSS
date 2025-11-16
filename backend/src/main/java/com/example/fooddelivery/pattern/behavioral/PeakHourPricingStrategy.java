package com.example.fooddelivery.pattern.behavioral;

import com.example.fooddelivery.domain.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Design Pattern: Strategy - Concrete Strategy
 * 
 * Adds surcharge during peak hours (11:00-14:00 and 18:00-21:00).
 */
@Component
public class PeakHourPricingStrategy implements PricingStrategy {
    private static final BigDecimal PEAK_HOUR_SURCHARGE = new BigDecimal("3.00");
    private static final LocalTime PEAK_START_1 = LocalTime.of(11, 0);
    private static final LocalTime PEAK_END_1 = LocalTime.of(14, 0);
    private static final LocalTime PEAK_START_2 = LocalTime.of(18, 0);
    private static final LocalTime PEAK_END_2 = LocalTime.of(21, 0);

    @Override
    public BigDecimal apply(BigDecimal basePrice, Order order) {
        LocalDateTime orderTime = order.getScheduledFor() != null 
            ? order.getScheduledFor() 
            : order.getCreatedAt();
        LocalTime time = orderTime.toLocalTime();
        
        boolean isPeakHour = (time.isAfter(PEAK_START_1) && time.isBefore(PEAK_END_1)) ||
                            (time.isAfter(PEAK_START_2) && time.isBefore(PEAK_END_2));
        
        if (isPeakHour) {
            return basePrice.add(PEAK_HOUR_SURCHARGE);
        }
        return basePrice;
    }
}

