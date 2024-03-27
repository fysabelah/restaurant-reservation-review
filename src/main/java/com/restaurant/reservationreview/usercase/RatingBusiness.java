package com.restaurant.reservationreview.usercase;

import com.restaurant.reservationreview.entities.Rating;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import org.springframework.stereotype.Component;

@Component
public class RatingBusiness {

    public Rating create(Rating rating, Restaurant restaurant) throws ValidationsException {
        rating.setDate(TimeUtils.now());

        if (restaurant == null || restaurant.getId() == null) {
            throw new ValidationsException("0101");
        }

        rating.setRestaurant(restaurant);

        return rating;
    }

    public Rating update(Rating saved, Integer score, String comment) throws ValidationsException {
        if (saved == null) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0103"));
        }

        saved.setComment(comment);

        if (score != null) {
            if (score < 0 || score > 10) {
                throw new ValidationsException("0100");
            }

            saved.setScore(score);
        }

        return saved;
    }
}