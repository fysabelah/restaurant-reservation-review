package com.restaurant.reservationreview.interfaceadapters.controllers;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.entities.ReservationControl;
import com.restaurant.reservationreview.interfaceadapters.gateways.ReservationControlGateway;
import com.restaurant.reservationreview.interfaceadapters.gateways.ReservationGateway;
import com.restaurant.reservationreview.interfaceadapters.presenters.ReservationControlPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.ReservationPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationControlDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ReservationControlController {

    private final static Integer PLUS_ONE_DAY = 1;


    @Resource
    private ReservationControlGateway reservationControlGateway;

    @Resource
    private ReservationGateway reservationGateway;

    @Resource
    private ReservationPresenter reservationPresenter;

    @Resource
    private ReservationControlPresenter reservationControlPresenter;

    public PagedResponse<ReservationControlDto> findAllReservationControlByRestaurantAndDateBetween(Pagination page, String restaurant, LocalDate start, LocalDate finish) throws ValidationsException {
        validateId(restaurant);

        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize());

        int dayOfYear;
        int year;

        dayOfYear = start.getDayOfYear();
        year = start.getYear();

        LocalDateTime startDate = LocalDate.ofYearDay(year, dayOfYear).atStartOfDay();

        dayOfYear = finish.getDayOfYear();
        year = finish.getYear();

        LocalDateTime finishDate = LocalDate.ofYearDay(year, dayOfYear).plusDays(PLUS_ONE_DAY).atStartOfDay();

        Page<ReservationControl> reservationControl = reservationControlGateway.findAllReservationControlByRestaurantAndDateBetween(restaurant, startDate, finishDate, pageable);

        return reservationControlPresenter.convertDocuments(reservationControl);

    }

    public PagedResponse<ReservationDto> findAllReservationsByRestaurantAndDateBetween(Pagination page, String restaurant, LocalDate start, LocalDate finish) throws ValidationsException {
        validateId(restaurant);

        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize());

        int dayOfYear;
        int year;

        dayOfYear = start.getDayOfYear();
        year = start.getYear();

        LocalDateTime startDate = LocalDate.ofYearDay(year, dayOfYear).atStartOfDay();

        dayOfYear = finish.getDayOfYear();
        year = finish.getYear();

        LocalDateTime finishDate = LocalDate.ofYearDay(year, dayOfYear).plusDays(PLUS_ONE_DAY).atStartOfDay();

        Page<Reservation> reservations = reservationGateway.findAllReservationsByRestaurantAndDateBetween(restaurant, startDate, finishDate, pageable);

        return reservationPresenter.convertDocuments(reservations);

    }

    private static void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0002"));
        }
    }

}
