package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.ReservationHours;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationHoursDto;
import org.springframework.stereotype.Component;

@Component
public class ReservationHoursPresenter implements Presenter<ReservationHours, ReservationHoursDto> {

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