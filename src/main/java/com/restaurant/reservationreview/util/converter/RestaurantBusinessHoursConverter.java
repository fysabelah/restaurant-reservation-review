package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.util.dto.RestaurantBusinessHoursDto;
import com.restaurant.reservationreview.model.documents.RestaurantBusinessHours;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class RestaurantBusinessHoursConverter implements Converter<RestaurantBusinessHours, RestaurantBusinessHoursDto> {

    @Override
    public RestaurantBusinessHoursDto convert(RestaurantBusinessHours document){

        RestaurantBusinessHoursDto dto = new RestaurantBusinessHoursDto();

        dto.setDayOfWeek(document.getDayOfWeek());
        dto.setStartTime(document.getStartTime().toString());
        dto.setFinishTime(document.getFinishTime().toString());

        return dto;
    }

    @Override
    public RestaurantBusinessHours convert(RestaurantBusinessHoursDto dto){
        RestaurantBusinessHours restaurantBusinessHours = new RestaurantBusinessHours();

        restaurantBusinessHours.setDayOfWeek(dto.getDayOfWeek());
        restaurantBusinessHours.setStartTime(LocalTime.parse(dto.getStartTime(), DateTimeFormatter.ISO_LOCAL_TIME));
        restaurantBusinessHours.setFinishTime(LocalTime.parse(dto.getFinishTime(), DateTimeFormatter.ISO_LOCAL_TIME));

        return restaurantBusinessHours;
    }

}
