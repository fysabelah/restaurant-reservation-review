package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ReservationPresenter implements Presenter<Reservation, ReservationDto> {

    @Resource
    private RestaurantPresenter restaurantPresenter;

    @Resource
    private PersonPresenter personPresenter;

    @Override
    public ReservationDto convert(Reservation document) {
        return new ReservationDto(
                document.getId(),
                restaurantPresenter.convert(document.getRestaurant()),
                personPresenter.convert(document.getPerson()),
                document.getDateAndTime(),
                document.getDayOfWeek(),
                document.getReservationAmount());
    }

    @Override
    public Reservation convert(ReservationDto dto) {
        return new Reservation(
                dto.getId(),
                restaurantPresenter.convert(dto.getRestaurantDto()),
                personPresenter.convert(dto.getPersonDto()),
                dto.getDateAndTime(),
                dto.getDayOfWeek(),
                dto.getReservationAmount());

    }

}
