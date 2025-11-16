package com.example.fooddelivery.controller;

import com.example.fooddelivery.domain.MenuItem;
import com.example.fooddelivery.domain.Restaurant;
import com.example.fooddelivery.repository.MenuItemRepository;
import com.example.fooddelivery.repository.RestaurantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "http://localhost:3000")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public RestaurantController(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDto>> getAllRestaurants() {
        List<RestaurantDto> restaurants = restaurantRepository.findAll().stream()
            .map(this::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/{id}/menu-items")
    public ResponseEntity<List<MenuItemDto>> getMenuItems(@PathVariable Long id) {
        List<MenuItemDto> items = menuItemRepository.findByRestaurantId(id).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(@RequestBody RestaurantDto dto) {
        Restaurant restaurant = new Restaurant(dto.getName(), dto.getAddress());
        Restaurant saved = restaurantRepository.save(restaurant);
        return ResponseEntity.ok(toDto(saved));
    }

    private RestaurantDto toDto(Restaurant restaurant) {
        RestaurantDto dto = new RestaurantDto();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setAddress(restaurant.getAddress());
        return dto;
    }

    private MenuItemDto toDto(MenuItem item) {
        MenuItemDto dto = new MenuItemDto();
        dto.setId(item.getId());
        dto.setRestaurantId(item.getRestaurant().getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setBasePrice(item.getBasePrice());
        dto.setStock(item.getStock());
        return dto;
    }

    public static class RestaurantDto {
        private Long id;
        private String name;
        private String address;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class MenuItemDto {
        private Long id;
        private Long restaurantId;
        private String name;
        private String description;
        private java.math.BigDecimal basePrice;
        private Integer stock;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(Long restaurantId) {
            this.restaurantId = restaurantId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public java.math.BigDecimal getBasePrice() {
            return basePrice;
        }

        public void setBasePrice(java.math.BigDecimal basePrice) {
            this.basePrice = basePrice;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
        }
    }
}

