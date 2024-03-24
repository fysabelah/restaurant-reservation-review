package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.RestaurantDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RestaurantPresenter implements Presenter<Restaurant, RestaurantDto> {

    @Resource
    private BusinessHoursPresenter businessHoursPresenter;

    @Resource
    private AdressPresenter adressPresenter;
    @Override
    public RestaurantDto convert(Restaurant document) {

        return new RestaurantDto(
                document.getId(),
                document.getName(),
                adressPresenter.convert(document.getAdress()),
                document.getBusinnessHours()
                        .stream()
                        .map(business -> businessHoursPresenter.convert(business))
                        .collect(Collectors.toList()),
                document.getFoodType(),
                document.getCapacity()
        );
    }

    @Override
    public Restaurant convert(RestaurantDto dto) {
        return new Restaurant(dto.getId(),
                dto.getName(),
                adressPresenter.convert(dto.getAdressDto()),
                dto.getBusinnessHoursDto()
                        .stream()
                        .map(businessDto -> businessHoursPresenter.convert(businessDto))
                        .collect(Collectors.toList()),
                dto.getFoodType(),
                dto.getCapacity()
        );
    }

}