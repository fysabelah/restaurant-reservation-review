package com.restaurant.reservationreview.framework.db;

import com.restaurant.reservationreview.entities.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    Optional<Restaurant> findByNameEquals(String restaurantName);

    Optional<Restaurant> findById( String idRestaurante);

}