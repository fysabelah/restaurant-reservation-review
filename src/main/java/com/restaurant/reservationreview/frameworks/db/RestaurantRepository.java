package com.restaurant.reservationreview.frameworks.db;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.util.enums.FoodType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

    Restaurant findByNameEquals(String restaurantName);

    @Query(value = "{ $and: [ {'foodType': {$eq: ?0}}, '{'address': {'city': {$regex: ?1}, $options: 'i'}}]}")
    Page<Restaurant> findAllByFoodTypeAndAdressCityLike(FoodType foodType, String location, Pageable pageable);

    Page<Restaurant> findAllByFoodType(FoodType foodType, Pageable pageable);

    @Query(value = "{'adress.city': {$regex: ?0, $options: 'i'}}")
    Page<Restaurant> findAllByAdressCityLike(String location, Pageable pageable);
}
