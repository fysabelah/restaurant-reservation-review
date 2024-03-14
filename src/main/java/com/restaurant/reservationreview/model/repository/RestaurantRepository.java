package com.restaurant.reservationreview.model.repository;

import com.restaurant.reservationreview.model.documents.restaurant.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {

}
