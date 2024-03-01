package com.restaurant.reservationreview.repository;

import com.restaurant.reservationreview.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    Optional<Restaurant> findByNameEquals(String restaurantName);
}
