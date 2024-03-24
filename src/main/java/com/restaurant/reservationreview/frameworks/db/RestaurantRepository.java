package com.restaurant.reservationreview.frameworks.db;

import com.restaurant.reservationreview.entities.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    Restaurant findByNameEquals(String restaurantName);

    Optional<Restaurant> findById(String idRestaurante);
    
}
