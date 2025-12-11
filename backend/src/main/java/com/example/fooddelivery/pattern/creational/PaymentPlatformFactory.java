package com.example.fooddelivery.pattern.creational;

/**
 * Design Pattern: Abstract Factory - Abstract Factory Interface
 * 
 * Defines the interface for creating families of related payment objects.
 * Each concrete factory creates all components needed for a specific payment platform.
 */
public interface PaymentPlatformFactory {
    PaymentProvider createPaymentProvider();
    PaymentValidator createPaymentValidator();
    PaymentNotifier createPaymentNotifier();
    String getPlatformName();
}

