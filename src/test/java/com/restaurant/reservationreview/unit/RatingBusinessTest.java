package com.restaurant.reservationreview.unit;

import com.restaurant.reservationreview.TestUtils;
import com.restaurant.reservationreview.entities.Rating;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.usercase.RatingBusiness;
import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;


class RatingBusinessTest extends TestUtils {

    @Resource
    private RatingBusiness business;

    private static final String RESTAURANT_ID = "NEW_RESTAURANT_ID";

    @Test
    void shouldThrowOnUpdateScore() throws ValidationsException {
        Rating rating = getRating();

        try {
            business.update(rating, 39, null);
        } catch (ValidationsException e) {
            assertEquals(e.getMessage(), MessageUtil.getMessage("0100"));
        }
    }

    private static Rating getRating() throws ValidationsException {
        String id = new Random().toString();

        Rating rating = new Rating();
        rating.setId(id);
        rating.setScore(10);
        rating.setName("Teste");
        rating.setDate(TimeUtils.now());
        rating.setZoneId(TimeUtils.getZoneId());
        rating.setComment("Eu sou um comentário");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);

        rating.setRestaurant(restaurant);

        return rating;
    }

    @Test
    void shouldUpdateOnNullScore() throws ValidationsException {
        Rating rating = getRating();

        AtomicReference<Rating> result = new AtomicReference<>();
        String newComment = "novo comentário";

        assertDoesNotThrow(() -> {
            result.set(business.update(rating, null, newComment));
        });

        assertEquals(newComment, result.get().getComment());
        assertEquals(rating.getScore(), result.get().getScore());
    }

    @Test
    void testCreateRatingWithNullRestaurant() {
        Rating rating = new Rating();

        assertThrows(ValidationsException.class, () -> business.create(rating, null));
    }

    @Test
    void testCreateRating() throws ValidationsException {
        Rating rating = new Rating();

        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);

        Rating createdRating = business.create(rating, restaurant);

        assertNotNull(createdRating);
        assertNotNull(createdRating.getDate());
        assertEquals(restaurant, createdRating.getRestaurant());
    }
}