package com.restaurant.reservationreview.model.repository;

import com.restaurant.reservationreview.model.documents.restaurant.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    Page<Rating> findAllByRestaurantId(String restaurant, Pageable page);

}
