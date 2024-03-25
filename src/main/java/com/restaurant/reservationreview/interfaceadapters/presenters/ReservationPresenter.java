package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.Person;
import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.PersonDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ReservationPresenter implements Presenter<Reservation, ReservationDto> {


    @Resource
    private RestaurantPresenter restaurantPresenter;

    @Resource
    private AddressPresenter addressPresenter;

    @Override
    public ReservationDto convert(Reservation document) {
        ReservationDto dto = new ReservationDto();

        dto.setId(document.getId());
        dto.setPerson(new PersonDto(document.getPerson()));
        dto.setStatus(document.getStatus());
        dto.setRestaurant(document.getRestaurant().getName());
        dto.setAddress(addressPresenter.convert(document.getRestaurant().getAddress()));
        dto.setTables(document.getTables());
        dto.setDateAndTime(document.getDate().toString());
        dto.setDayOfWeek(document.getDayOfWeek());

        return dto;
    }

    @Override
    public Reservation convert(ReservationDto dto) {
        Reservation reservation = new Reservation();

        reservation.setId(dto.getId());
        reservation.setDate(TimeUtils.getDate(dto.getDateAndTime()));
        reservation.setPerson(new Person(dto.getPerson()));
        reservation.setTables(dto.getTables());

        reservation.setRestaurant(new Restaurant());

        reservation.getRestaurant().setId(dto.getRestaurantId());

        return reservation;
    }
}
