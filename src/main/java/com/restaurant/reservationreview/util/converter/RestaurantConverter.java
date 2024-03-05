package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.model.documents.Restaurant;
import com.restaurant.reservationreview.util.dto.RestaurantDto;
import org.springframework.stereotype.Component;

@Component
public class RestaurantConverter implements Converter<Restaurant, RestaurantDto> {

    @Override
    public RestaurantDto convert(Restaurant document) {
        return new RestaurantDto(document.getId());
    }

    @Override
    public Restaurant convert(RestaurantDto dto) {
        return new Restaurant(dto.getId());
    }
}
