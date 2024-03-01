package com.restaurant.reservationreview.command.restaurant;


import com.restaurant.reservationreview.dto.RestaurantDto;
import com.restaurant.reservationreview.model.Restaurant;
import com.restaurant.reservationreview.service.RestaurantServiceInterface;
import com.restaurant.reservationreview.util.converter.RestaurantConverter;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InsertRestaurantCommand {

    @Resource
    private RestaurantServiceInterface service;

    @Resource
    private RestaurantConverter converter;

    public RestaurantDto execute(RestaurantDto restaurantDto) throws ValidationsException {
        Restaurant restaurant = this.converter.convert(restaurantDto);

        Optional<Restaurant> saved = this.service.findByName(restaurant.getName());

        if (saved.isPresent()){
            throw new ValidationsException("Restaurant com nome" + restaurant.getName() +  " já cadastrado no código " + restaurant.getId());
        }

        restaurant = this.service.insert(restaurant);

        return this.converter.convert(restaurant);

    }

}
