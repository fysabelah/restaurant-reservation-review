package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.Rating;
import com.restaurant.reservationreview.frameworks.db.rating.RatingRepository;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class RatingGateway {

    @Resource
    private RatingRepository repository;

    public Rating insert(Rating rating) {
        return repository.insert(rating);
    }

    public Rating findById(String id) throws ValidationsException {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "avalia\u00E7\u00E3o", id));
    }

    public void delete(Rating rating) {
        repository.delete(rating);
    }

    public Rating update(Rating rating) {
        return repository.save(rating);
    }

    public Page<Rating> findByRestaurantId(String restaurant, Pageable page) {
        return repository.findAllByRestaurantId(restaurant, page);
    }

    public BigDecimal getRestaurantRating(String restaurantId) throws ValidationsException {
        if (restaurantId == null || restaurantId.trim().isEmpty()) {
            throw new ValidationsException("0004");
        }

        Map<String, BigDecimal> ratings = repository.calculateAvgScoreByRestaurant();

        if (ratings.containsKey(restaurantId)) {
            return ratings.get(restaurantId);
        }

        return BigDecimal.ZERO;
    }
}
