package com.restaurant.reservationreview.interfaceadapters.controllers;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.interfaceadapters.gateways.RestaurantGateway;
import com.restaurant.reservationreview.interfaceadapters.presenters.RestaurantPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
import com.restaurant.reservationreview.usercase.RestaurantBusiness;
import com.restaurant.reservationreview.util.enums.FoodType;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RestaurantController {

    @Resource
    private RestaurantGateway restaurantGateway;

    @Resource
    private RestaurantPresenter converter;

    @Resource
    private RestaurantBusiness business;

    public RestaurantDto insert(RestaurantDto restaurantDto) throws ValidationsException {
        Restaurant restaurant = this.converter.convert(restaurantDto);

        Restaurant restaurantNomeDuplicado = restaurantGateway.findByName(restaurant.getName());

        this.business.create(restaurantNomeDuplicado);

        restaurant = this.restaurantGateway.insert(restaurant);

        return this.converter.convert(restaurant);
    }

    public PagedResponse<RestaurantDto> findAll(Pagination pagination, FoodType foodType, String location) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize());

        Page<Restaurant> restaurants;

        boolean isValidLocation = location != null && !location.trim().isEmpty();
        boolean isValidFoodType = foodType != null;

        if (!isValidLocation && !isValidFoodType) {
            restaurants = this.restaurantGateway.findAll(pageable);
        } else {
            if (isValidLocation && isValidFoodType) {
                restaurants = restaurantGateway.findAll(foodType, location, pageable);
            } else {
                if (isValidLocation) {
                    restaurants = this.restaurantGateway.findAll(location, pageable);
                } else {
                    restaurants = restaurantGateway.findAll(foodType, pageable);
                }
            }
        }

        return this.converter.convertDocuments(restaurants);
    }

    public RestaurantDto findById(String idRestaurant) throws ValidationsException {
        Restaurant restaurant = this.restaurantGateway.findById(idRestaurant);

        return this.converter.convert(restaurant);
    }

    public RestaurantDto update(String idRestaurant, RestaurantDto restaurantDto) throws ValidationsException {
        // business irá verificar se o Id enviado na requisição está correto e retorna as informações atuais do restaurant
        Restaurant restaurant = restaurantGateway.findById(idRestaurant);

        Restaurant restaurantWithTheSameName = restaurantGateway.findByName(restaurantDto.getName());

        Restaurant toUpdate = this.converter.convert(restaurantDto);

        this.business.update(restaurant, toUpdate, restaurantWithTheSameName);

        restaurant = this.restaurantGateway.update(restaurant);

        return this.converter.convert(restaurant);
    }
}