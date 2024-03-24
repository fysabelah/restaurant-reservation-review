package com.restaurant.reservationreview.frameworks.db.rating;

import com.restaurant.reservationreview.entities.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String>, RatingRepositoryCustom {

    Page<Rating> findAllByRestaurantId(String restaurant, Pageable page);

}
