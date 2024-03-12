package com.restaurant.reservationreview.command.restaurant;

import com.restaurant.reservationreview.util.dto.RestaurantDto;
import com.restaurant.reservationreview.model.documents.Restaurant;
import com.restaurant.reservationreview.controller.service.RestaurantServiceInterface;
import com.restaurant.reservationreview.util.converter.RestaurantConverter;
import com.restaurant.reservationreview.util.exception.NotFoundException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UpdateRestaurantCommand {
    @Resource
    private RestaurantServiceInterface service;

    @Resource
    private RestaurantConverter converter;

    public RestaurantDto execute(RestaurantDto restaurantDto) throws NotFoundException {
        Optional<Restaurant> saved = this.service.findById(restaurantDto.getId());

        if (saved.isEmpty()){
            throw new NotFoundException(restaurantDto.getId(), "Restaurant");
        }

        Restaurant restaurant = saved.get();

        restaurant.setName(restaurantDto.getName());
        restaurant.setLocation(restaurantDto.getLocation());
        restaurant.setCuisineType(restaurantDto.getCuisineType());
        restaurant.setActive(restaurantDto.getActive());
        restaurant.setQuantityTables(restaurantDto.getQuantitytables());

        restaurant = this.service.update(restaurant);

        return this.converter.convert(restaurant);

    }

}
