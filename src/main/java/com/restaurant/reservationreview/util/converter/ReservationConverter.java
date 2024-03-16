package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.model.documents.reservation.Reservation;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.dto.reservation.ReservationDto;
import jakarta.annotation.Resource;

public class ReservationConverter implements Converter<Reservation, ReservationDto> {

    @Resource
    private RestaurantConverter restaurantConverter;

    @Resource
    private PersonConverter personConverter;

    @Override
    public ReservationDto convert(Reservation document) {
        return new ReservationDto(restaurantConverter.convert(document.getRestaurant()),
                personConverter.convert(document.getPerson()),
                document.getDateAndTime(),
                document.getDayOfWeek(),
                document.getReservationAmount());
    }

    @Override
    public Reservation convert(ReservationDto dto) {
        Reservation reservation = new Reservation();

        reservation.setRestaurant(restaurantConverter.convert(dto.getRestaurantDto()));
        reservation.setPerson(personConverter.convert(dto.getPersonDto()));
        reservation.setDateAndTime(dto.getDateAndTime());
        reservation.setDayOfWeek(dto.getDayOfWeek());
        reservation.setReservationAmount(dto.getReservationAmount());

        return reservation;
    }

}
