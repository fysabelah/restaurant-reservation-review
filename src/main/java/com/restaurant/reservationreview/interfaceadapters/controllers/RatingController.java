package com.restaurant.reservationreview.interfaceadapters.controllers;

import com.restaurant.reservationreview.entities.Rating;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.interfaceadapters.gateways.RatingGateway;
import com.restaurant.reservationreview.interfaceadapters.gateways.RestaurantGateway;
import com.restaurant.reservationreview.interfaceadapters.presenters.RatingPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RatingDto;
import com.restaurant.reservationreview.usercase.RatingBusiness;
import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RatingController {

    @Resource
    private RatingPresenter converter;

    @Resource
    private RatingBusiness business;

    @Resource
    private RatingGateway gateway;

    @Resource
    private RestaurantGateway restaurantGateway;

    public RatingDto insert(String restaurantId, RatingDto dto) throws ValidationsException {
        validateId(restaurantId);

        Restaurant restaurant = restaurantGateway.findById(restaurantId);

        Rating rating = converter.convert(dto);

        rating = business.create(rating, restaurant);

        rating = gateway.insert(rating);

        restaurant.updateRating(new BigDecimal(rating.getScore()));

        restaurantGateway.update(restaurant);

        return converter.convert(rating);
    }

    public PagedResponse<RatingDto> findAll(Pagination page, String restaurant) throws ValidationsException {
        validateId(restaurant);

        restaurantGateway.findById(restaurant);

        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize());

        Page<Rating> ratings = gateway.findByRestaurantId(restaurant, pageable);

        return converter.convertDocuments(ratings);

    }

    public RatingDto findById(String id) throws ValidationsException {
        validateId(id);

        Rating rating = gateway.findById(id);

        return converter.convert(rating);
    }

    private static void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0002"));
        }
    }

    public void delete(String id) throws ValidationsException {
        validateId(id);

        Rating rating = gateway.findById(id);

        gateway.delete(rating);
    }

    public RatingDto update(String id, Integer score, String comment) throws ValidationsException {
        validateId(id);

        Rating rating = gateway.findById(id);

        rating = business.update(rating, score, comment);

        rating = gateway.update(rating);

        return converter.convert(rating);
    }

}