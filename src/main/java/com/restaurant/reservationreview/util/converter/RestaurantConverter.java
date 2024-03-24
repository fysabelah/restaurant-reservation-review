package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.entities.RestaurantBusinessHours;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantBusinessHoursDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
import com.restaurant.reservationreview.entities.Restaurant;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantConverter implements Converter<Restaurant, RestaurantDto> {
    @Resource
    private RestaurantBusinessHoursConverter restaurantBusinessHoursConverter;

    @Override
    public RestaurantDto convert(Restaurant document){
        RestaurantDto dto = new RestaurantDto();

        dto.setId(document.getId());
        dto.setName(document.getName());
        dto.setLocation(document.getLocation());
        dto.setFoodType(document.getFoodType());
        dto.setActive(document.getActive());
        dto.setAverageRating(document.getAverageRating());
        dto.setQuantityTables(document.getQuantityTables());

        List<RestaurantBusinessHoursDto> BusinessHours= this.restaurantBusinessHoursConverter.convertDocuments(document.getBusinessHours());
        dto.setBusinessHoursDto(BusinessHours);

        return dto;

    }

    @Override
    public Restaurant convert(RestaurantDto dto) {

        Restaurant restaurant = new Restaurant();

        restaurant.setId(dto.getId());
        restaurant.setName(dto.getName());
        restaurant.setLocation(dto.getLocation());
        restaurant.setFoodType(dto.getFoodType());
        restaurant.setActive(dto.getActive());
        restaurant.setAverageRating(dto.getAverageRating());
        restaurant.setQuantityTables(dto.getQuantityTables());

        List<RestaurantBusinessHours> businessHours =this.restaurantBusinessHoursConverter.convertDto(dto.getBusinessHoursDto());
        restaurant.setBusinessHours(businessHours);

        return restaurant;

    }

}
