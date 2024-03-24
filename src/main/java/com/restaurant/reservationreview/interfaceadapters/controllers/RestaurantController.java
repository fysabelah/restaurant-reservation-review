
package com.restaurant.reservationreview.interfaceadapters.controllers;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.interfaceadapters.gateways.RatingGateway;
import com.restaurant.reservationreview.interfaceadapters.gateways.ReservationGateway;
import com.restaurant.reservationreview.interfaceadapters.gateways.RestaurantGateway;
import com.restaurant.reservationreview.interfaceadapters.presenters.RestaurantPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
import com.restaurant.reservationreview.usercase.RestaurantBusiness;
import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class RestaurantController {

    @Resource
    private RestaurantGateway restaurantGateway;

    @Resource
    private RestaurantPresenter converter;

    @Resource
    private RestaurantBusiness business;

    @Resource
    private RatingGateway ratingGateway;

    @Resource
    private ReservationGateway reservationGateway;

    public RestaurantDto insert(RestaurantDto restaurantDto) throws ValidationsException {
        business.create(restaurantGateway.findByName(restaurantDto.getName()));

        Restaurant restaurant = this.converter.convert(restaurantDto);

        restaurant = this.restaurantGateway.insert(restaurant);

        return this.converter.convert(restaurant);
    }

    public PagedResponse<RestaurantDto> findAll(Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize());

        Page<Restaurant> restaurants = this.restaurantGateway.findAll(pageable);

        Map<String, BigDecimal> ratings = new HashMap<>();

        restaurants.get().forEach(restaurant -> {
            try {
                BigDecimal score = ratingGateway.getRestaurantRating(restaurant.getId());

                ratings.put(restaurant.getId(), score);
            } catch (ValidationsException e) {
                throw new RuntimeException(e);
            }
        });

        return this.converter.convertDocuments(restaurants, ratings);
    }

    public RestaurantDto findById(String idRestaurant) throws ValidationsException {
        Restaurant restaurant = this.restaurantGateway.findById(idRestaurant);
        BigDecimal rating = ratingGateway.getRestaurantRating(idRestaurant);

        return this.converter.convert(restaurant, rating);
    }

    public RestaurantDto update(RestaurantDto restaurantDto) throws ValidationsException {
        if (restaurantDto.getId() == null) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0004"));
        }

        Restaurant restaurant = this.restaurantGateway.findById(restaurantDto.getId());

        Optional<Restaurant> optional = restaurantGateway.findByName(restaurantDto.getName());

        Restaurant toUpdate = converter.convert(restaurantDto);

        restaurant = business.update(optional, restaurant, toUpdate);

        restaurant = restaurantGateway.update(restaurant);

        return this.converter.convert(restaurant);
    }

    public void disable(String id) throws ValidationsException {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0004"));
        }

        Restaurant restaurant = restaurantGateway.findById(id);

        // TODO precisar validar isso aqui
        List<Reservation> reservations = reservationGateway.findAllByRestaurantIdAndDate(id, TimeUtils.now());

        business.disable(reservations);

        restaurantGateway.disable(restaurant);
    }
}
