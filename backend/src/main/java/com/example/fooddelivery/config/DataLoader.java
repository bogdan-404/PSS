package com.example.fooddelivery.config;

import com.example.fooddelivery.domain.*;
import com.example.fooddelivery.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Data initializer that creates demo data for testing the application.
 */
@Component
public class DataLoader implements CommandLineRunner {
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final CourierRepository courierRepository;

    public DataLoader(
            CustomerRepository customerRepository,
            RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository,
            CourierRepository courierRepository) {
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.courierRepository = courierRepository;
    }

    @Override
    public void run(String... args) {
        if (customerRepository.count() == 0) {
            // Create customers
            Customer customer1 = new Customer("John Doe", "john@example.com", "123 Main St, City");
            Customer customer2 = new Customer("Jane Smith", "jane@example.com", "456 Oak Ave, City");
            customerRepository.save(customer1);
            customerRepository.save(customer2);

            // Create restaurants
            Restaurant restaurant1 = new Restaurant("Pizza Palace", "789 Restaurant Blvd, City");
            Restaurant restaurant2 = new Restaurant("Burger House", "321 Food Street, City");
            Restaurant restaurant3 = new Restaurant("Sushi World", "654 Sushi Lane, City");
            restaurantRepository.save(restaurant1);
            restaurantRepository.save(restaurant2);
            restaurantRepository.save(restaurant3);

            // Create menu items for restaurant1
            MenuItem item1 = new MenuItem(restaurant1, "Margherita Pizza", "Classic tomato and mozzarella", new BigDecimal("12.99"));
            MenuItem item2 = new MenuItem(restaurant1, "Pepperoni Pizza", "Pepperoni and cheese", new BigDecimal("14.99"));
            MenuItem item3 = new MenuItem(restaurant1, "Hawaiian Pizza", "Ham and pineapple", new BigDecimal("15.99"));
            menuItemRepository.save(item1);
            menuItemRepository.save(item2);
            menuItemRepository.save(item3);

            // Create menu items for restaurant2
            MenuItem item4 = new MenuItem(restaurant2, "Classic Burger", "Beef patty with lettuce and tomato", new BigDecimal("9.99"));
            MenuItem item5 = new MenuItem(restaurant2, "Cheeseburger", "Beef patty with cheese", new BigDecimal("10.99"));
            MenuItem item6 = new MenuItem(restaurant2, "Bacon Burger", "Beef patty with bacon", new BigDecimal("12.99"));
            menuItemRepository.save(item4);
            menuItemRepository.save(item5);
            menuItemRepository.save(item6);

            // Create menu items for restaurant3
            MenuItem item7 = new MenuItem(restaurant3, "Salmon Sushi Roll", "Fresh salmon roll", new BigDecimal("8.99"));
            MenuItem item8 = new MenuItem(restaurant3, "Tuna Sushi Roll", "Fresh tuna roll", new BigDecimal("9.99"));
            MenuItem item9 = new MenuItem(restaurant3, "Dragon Roll", "Eel and avocado roll", new BigDecimal("13.99"));
            menuItemRepository.save(item7);
            menuItemRepository.save(item8);
            menuItemRepository.save(item9);

            // Create couriers
            Courier courier1 = new Courier("Mike Johnson", "100 Courier St, City");
            Courier courier2 = new Courier("Sarah Williams", "200 Delivery Ave, City");
            courierRepository.save(courier1);
            courierRepository.save(courier2);
        }
    }
}

