package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.model.documents.restaurant.ReservationHours;
import com.restaurant.reservationreview.util.dto.restaurant.ReservationHoursDto;
import org.springframework.stereotype.Component;

@Component
public class ReservationHoursConverter implements Converter<ReservationHours, ReservationHoursDto> {

    @Override
    public ReservationHoursDto convert(ReservationHours document){

        return new ReservationHoursDto(
                document.getHour(),
                document.getTableAmountAvailable()
        );
    }

    @Override
    public ReservationHours convert(ReservationHoursDto dto){

        return new ReservationHours(
                dto.getHour(),
                dto.getTableAmountAvailable()
        );

    }

}
