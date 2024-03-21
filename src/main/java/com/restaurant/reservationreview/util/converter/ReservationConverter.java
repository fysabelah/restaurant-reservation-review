package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.model.documents.reservation.Reservation;
import com.restaurant.reservationreview.util.dto.reservation.ReservationDto;
import jakarta.annotation.Resource;

public class ReservationConverter implements Converter<Reservation, ReservationDto> {

    @Resource
    private RestaurantConverter restaurantConverter;

    @Resource
    private PersonConverter personConverter;

    @Override
    public ReservationDto convert(Reservation document) {
        return new ReservationDto(
                document.getId(),
                restaurantConverter.convert(document.getRestaurant()),
                personConverter.convert(document.getPerson()),
                document.getDateAndTime(),
                document.getDayOfWeek(),
                document.getReservationAmount());
    }

    @Override
    public Reservation convert(ReservationDto dto) {
        return new Reservation(
                dto.getId(),
                restaurantConverter.convert(dto.getRestaurantDto()),
                personConverter.convert(dto.getPersonDto()),
                dto.getDateAndTime(),
                dto.getDayOfWeek(),
                dto.getReservationAmount());

    }

}
