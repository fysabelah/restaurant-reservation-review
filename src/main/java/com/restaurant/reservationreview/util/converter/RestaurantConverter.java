package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.dto.RestaurantDto;
import com.restaurant.reservationreview.model.Restaurant;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import org.springframework.stereotype.Component;

@Component
public class RestaurantConverter implements Converter<Restaurant, RestaurantDto> {

    @Override
    public RestaurantDto convert(Restaurant entity){
        RestaurantDto dto = new RestaurantDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLocation(entity.getLocation());
        dto.setCuisineType(entity.getCuisineType());
        dto.setActive(entity.getActive());
        dto.setAverageRating(entity.getAverageRating());
        dto.setManytables(entity.getManytables());

        return dto;

    }

    @Override
    public Restaurant convert(RestaurantDto dto) throws ValidationsException {

        Restaurant restaurant = new Restaurant();

        restaurant.setId(dto.getId());
        restaurant.setName(dto.getName());
        restaurant.setLocation(dto.getLocation());
        restaurant.setCuisineType(dto.getCuisineType());
        restaurant.setActive(dto.getActive());
        restaurant.setAverageRating(dto.getAverageRating());
        restaurant.setManytables(dto.getManytables());

        return restaurant;

    }

}
