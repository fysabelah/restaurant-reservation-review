package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.ReservationControl;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationControlDto;
import org.springframework.stereotype.Component;

@Component
public class ReservationControlPresenter implements Presenter<ReservationControl, ReservationControlDto>{

    @Override
    public ReservationControlDto convert(ReservationControl document) {
        return new ReservationControlDto(
                document.getId(),
                document.getRestaurant().getId(),
                document.getRestaurant().getName(),
                document.getDateAndTime(),
                document.getDayOfWeek(),
                document.getTotalReservations(),
                document.getCapacity(),
                document.isAvailable());

    }

    @Override
    public ReservationControl convert(ReservationControlDto dto) {
        return new ReservationControl(
                dto.getId(),
                dto.getRestaurantId(),
                dto.getDateAndTime(),
                dto.getDayOfWeek(),
                dto.getTotalReservations(),
                dto.getCapacity(),
                dto.isAvailable());

    }

}
