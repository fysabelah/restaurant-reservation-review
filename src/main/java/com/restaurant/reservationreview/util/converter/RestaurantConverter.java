package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.model.documents.restaurant.Restaurant;
import com.restaurant.reservationreview.util.dto.restaurant.RestaurantDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RestaurantConverter implements Converter<Restaurant, RestaurantDto> {

    @Resource
    private BusinessHoursConverter businessHoursConverter;

    @Override
    public RestaurantDto convert(Restaurant document) {

        return new RestaurantDto(document.getId(),
                document.getBusinnessHours()
                        .stream()
                        .map(business -> businessHoursConverter.convert(business))
                        .collect(Collectors.toList()),
                document.getFoodType(),
                document.getTableAmount()
        );
    }

    @Override
    public Restaurant convert(RestaurantDto dto) {
        return new Restaurant(dto.getId(),
                dto.getBusinnessHoursDto()
                        .stream()
                        .map(business -> businessHoursConverter.convert(business))
                        .collect(Collectors.toList()),
//                businessHoursConverter.convert(dto.getBusinnessHoursDto()),
                dto.getFoodType(),
                dto.getTableAmount()
        );
    }

}
