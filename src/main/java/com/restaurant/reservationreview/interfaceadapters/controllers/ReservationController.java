package com.restaurant.reservationreview.interfaceadapters.controllers;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.interfaceadapters.gateways.ReservationGateway;
import com.restaurant.reservationreview.interfaceadapters.gateways.RestaurantGateway;
import com.restaurant.reservationreview.interfaceadapters.presenters.ReservationPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.usercase.ReservationBusinness;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.enums.StatusReservation;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationController {

    @Resource
    private ReservationGateway gateway;

    @Resource
    private ReservationPresenter presenter;

    @Resource
    private ReservationBusinness businness;

    @Resource
    private RestaurantGateway restaurantGateway;

    public ReservationDto insert(ReservationDto reservationDto) throws ValidationsException {
        Reservation reservation = presenter.convert(reservationDto);
        Restaurant restaurant = restaurantGateway.findById(reservation.getRestaurant().getId());

        businness.validateOpeningHoursAndRestaurante(reservation, restaurant);

        List<Reservation> reservations = gateway.findAllReservationOfPeriod(restaurant.getId(),
                reservation.getDate(),
                TimeUtils.addHoursToLocalDateTime(reservation.getDate(), restaurant.getHourToConsiderReservation())
        );

        businness.validateTables(reservations, restaurant.getQuantityTablesAvailableReservation(), reservation.getTables());

        businness.create(reservation, restaurant);

        reservation = gateway.insert(reservation);

        return presenter.convert(reservation);
    }

    public PagedResponse<ReservationDto> findAll(Pagination pagination, String restaurant, String person) {
        Pageable pageable = PageRequest.of(pagination.getPage(), pagination.getPageSize());

        Page<Reservation> page = gateway.findAll(restaurant, person, pageable);

        return presenter.convertDocuments(page);
    }

    public ReservationDto findById(String id) throws ValidationsException {
        Reservation reservation = gateway.findById(id);

        return presenter.convert(reservation);
    }

    public ReservationDto updateStatus(String id, StatusReservation status) throws ValidationsException {
        Reservation reservation = gateway.findById(id);

        businness.updateStatus(reservation.getStatus());

        reservation.setStatus(status);

        reservation = gateway.update(reservation);

        return presenter.convert(reservation);
    }
}
