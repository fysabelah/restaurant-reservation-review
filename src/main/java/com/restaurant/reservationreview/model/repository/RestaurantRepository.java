package com.restaurant.reservationreview.model.repository;

import com.restaurant.reservationreview.model.documents.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    Optional<Restaurant> findByNameEquals(String restaurantName);
    
}
