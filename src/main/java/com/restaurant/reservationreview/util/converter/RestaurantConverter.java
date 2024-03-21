package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.model.documents.BusinnessHours;
import com.restaurant.reservationreview.model.documents.Restaurant;
import com.restaurant.reservationreview.util.dto.BusinnessHoursDto;
import com.restaurant.reservationreview.util.dto.RestaurantDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RestaurantConverter implements Converter<Restaurant, RestaurantDto> {

    @Resource
    private BusinessHoursConverter businessHoursConverter;

    @Resource
    private AdressConverter adressConverter;
    @Override
    public RestaurantDto convert(Restaurant document) {

        return new RestaurantDto(
                document.getId(),
                document.getName(),
                adressConverter.convert(document.getAdress()),
                document.getBusinnessHours()
                        .stream()
                        .map(business -> businessHoursConverter.convert(business))
                        .collect(Collectors.toList()),
                document.getFoodType(),
                document.getCapacity()
        );
    }

    @Override
    public Restaurant convert(RestaurantDto dto) {
        return new Restaurant(dto.getId(),
                dto.getName(),
                adressConverter.convert(dto.getAdressDto()),
                dto.getBusinnessHoursDto()
                        .stream()
                        .map(businessDto -> businessHoursConverter.convert(businessDto))
                        .collect(Collectors.toList()),
                dto.getFoodType(),
                dto.getCapacity()
        );
    }

}
