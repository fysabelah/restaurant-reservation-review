package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
import org.springframework.stereotype.Component;

@Component
public class RestaurantPresenter implements Presenter<Restaurant, RestaurantDto> {

    @Override
    public RestaurantDto convert(Restaurant document) {
        return new RestaurantDto(document.getId());
    }

    @Override
    public Restaurant convert(RestaurantDto dto) {
        return new Restaurant(dto.getId());
    }
}