package com.restaurant.reservationreview.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.reservationreview.TestUtils;
import com.restaurant.reservationreview.controller.service.RatingService;
import com.restaurant.reservationreview.model.documents.Rating;
import com.restaurant.reservationreview.model.documents.Restaurant;
import com.restaurant.reservationreview.model.repository.RatingRepository;
import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;


class RatingServiceTest extends TestUtils {

    @Resource
    private ObjectMapper objectMapper;

    @MockBean
    private RatingRepository ratingRepository;

    @Resource
    private RatingService service;

    private static final String RESTAURANT_ID = "NEW_RESTAURANT_ID";

    @Test
    void shouldThrowOnFindById() {
        Mockito.when(ratingRepository.findById(any(String.class)))
                .thenReturn(Optional.empty());

        assertThrows(ValidationsException.class, () -> service.findById(new Random().toString()));
    }

    @Test
    void shouldThrowOnUpdateScore() {
        Mockito.when(ratingRepository.findById(any(String.class)))
                .thenReturn(Optional.of(new Rating()));

        try {
            service.update(new Random().toString(), 39, null);
        } catch (ValidationsException e) {
            assertEquals(e.getMessage(), MessageUtil.getMessage("0100"));
        }
    }

    @Test
    void shouldUpdateOnNullScore() {
        Rating rating = new Rating();
        rating.setId("id");
        rating.setScore(5);
        rating.setComment("novo comentário");
        rating.setName("José");
        rating.setDate(LocalDateTime.now());
        rating.setRestaurant(new Restaurant(RESTAURANT_ID));
        rating.setZoneId(TimeUtils.getZoneId());

        Mockito.when(ratingRepository.findById(any(String.class)))
                .thenReturn(Optional.of(new Rating()));

        Mockito.when(ratingRepository.save(any(Rating.class)))
                .thenReturn(rating);

        assertDoesNotThrow(() -> service.update("id", null, "novo comentário"));
    }
}