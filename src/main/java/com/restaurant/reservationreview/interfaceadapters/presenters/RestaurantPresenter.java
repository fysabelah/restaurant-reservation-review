package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.entities.RestaurantBusinessHours;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantBusinessHoursDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantPresenter implements Presenter<Restaurant, RestaurantDto> {

    @Resource
    private RestaurantBusinessHoursConverter restaurantBusinessHoursConverter;

    @Override
    public RestaurantDto convert(Restaurant document) {
        RestaurantDto dto = new RestaurantDto();

        dto.setId(document.getId());
        dto.setName(document.getName());
        dto.setLocation(document.getLocation());
        dto.setFoodType(document.getFoodType());
        dto.setActive(document.isActive());
        dto.setRating(document.getRating());
        dto.setQuantityTables(document.getQuantityTables());

        List<RestaurantBusinessHoursDto> businessHoursDtos = this.restaurantBusinessHoursConverter.convertDocuments(document.getBusinessHours());
        dto.setBusinessHoursDto(businessHoursDtos);

        return dto;

    }

    @Override
    public Restaurant convert(RestaurantDto dto) {

        Restaurant restaurant = new Restaurant();

        restaurant.setId(dto.getId());
        restaurant.setName(dto.getName());
        restaurant.setLocation(dto.getLocation());
        restaurant.setFoodType(dto.getFoodType());
        restaurant.setActive(dto.isActive());
        restaurant.setRating(dto.getRating());
        restaurant.setQuantityTables(dto.getQuantityTables());

        List<RestaurantBusinessHours> businessHours = this.restaurantBusinessHoursConverter.convert(dto.getBusinessHoursDto());
        restaurant.setBusinessHours(businessHours);

        return restaurant;

    }
}