package com.restaurant.reservationreview.interfaceadapters.controllers;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.interfaceadapters.gateways.RestaurantGateway;
import com.restaurant.reservationreview.interfaceadapters.presenters.RestaurantPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class RestaurantController {

    @Resource
    private RestaurantGateway restaurantGateway;

    @Resource
    private RestaurantPresenter converter;

    public RestaurantDto insert(RestaurantDto restaurantDto) throws ValidationsException {
        Restaurant restaurant = this.converter.convert(restaurantDto);

        restaurant = this.restaurantGateway.insert(restaurant);

        return this.converter.convert(restaurant);
    }

    public PagedResponse<RestaurantDto> findAll(Pagination pagination) {

        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize());

        Page<Restaurant> restaurants = this.restaurantGateway.findAll(pageable);

        return this.converter.convertDocuments(restaurants);

    }

    public RestaurantDto findById(String idRestaurant) throws ValidationsException {
        Restaurant restaurant = this.restaurantGateway.findById(idRestaurant);
        return this.converter.convert(restaurant);
    }

    public RestaurantDto update(String idRestaurant, RestaurantDto restaurantDto) throws ValidationsException {
        Restaurant restaurant = this.converter.convert(restaurantDto);

        restaurant = this.restaurantGateway.update(idRestaurant, restaurant);

        return this.converter.convert(restaurant);
    }
}
