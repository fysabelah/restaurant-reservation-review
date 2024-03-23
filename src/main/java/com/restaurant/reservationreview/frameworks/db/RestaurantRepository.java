package com.restaurant.reservationreview.framework.db;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.util.enums.FoodType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    Restaurant findByNameEquals(String restaurantName);

    @Query(value = "{'foodType': ?0, 'location': {$regex: '(?i)?1'}}")
    Page<Restaurant> findAllByFoodTypeAndLocationLike(FoodType foodType, String location, Pageable pageable);

    Page<Restaurant> findAllByFoodType(FoodType foodType, Pageable pageable);

    @Query(value = "{'location': {$regex: '(?i)?0'}}")
    Page<Restaurant> findAllByLocationLike(String location, Pageable pageable);
}
