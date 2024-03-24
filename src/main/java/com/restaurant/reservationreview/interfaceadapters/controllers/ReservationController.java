package com.restaurant.reservationreview.interfaceadapters.controllers;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.entities.ReservationControl;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.interfaceadapters.gateways.ReservationControlGateway;
import com.restaurant.reservationreview.interfaceadapters.gateways.ReservationGateway;
import com.restaurant.reservationreview.interfaceadapters.gateways.RestaurantGateway;
import com.restaurant.reservationreview.interfaceadapters.presenters.ReservationPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.PersonDto;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.usercase.ReservationControlBusiness;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import com.restaurant.reservationreview.util.pagination.PagedResponse;
import com.restaurant.reservationreview.util.pagination.Pagination;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Component
public class ReservationController {

    private final static Integer PLUS_ONE_DAY = 1;

    private final static Integer PLUS_RESERVATION_DAYS = 30;

    @Resource
    private RestaurantGateway restaurantGateway;

    @Resource
    private ReservationControlGateway reservationControlGateway;

    @Resource
    private ReservationGateway reservationGateway;

    @Resource
    private ReservationPresenter reservationPresenter;

    @Resource
    private ReservationControlBusiness reservationControlBusiness;

    public List<LocalDate> checkAvailableDates(String restaurantId, Integer table) throws ValidationsException {

        Restaurant restaurant = restaurantGateway.findById(restaurantId);

        List<LocalDate> dates;
        LocalDateTime startDate = LocalDate.now().plusDays(PLUS_ONE_DAY).atStartOfDay();
        LocalDateTime finishDate = LocalDate.now().plusDays(PLUS_RESERVATION_DAYS).atStartOfDay();

        Optional<List<ReservationControl>> reservations = reservationControlGateway.findReservationsByRestaurantAndDateNextDays(restaurantId, startDate, finishDate);

        if (reservations.get().isEmpty()) {

            dates = reservationControlBusiness.nextDaysList(restaurant);

        }else{

            dates = reservationControlBusiness.checkDateAvailability(restaurant, reservations.get(), table);

        }

        return dates;

    }

    public List<LocalTime> checkAvailableHours(String restaurantId, Integer table, LocalDate date) throws ValidationsException {

        Restaurant restaurant = restaurantGateway.findById(restaurantId);

        List<LocalTime> availableHours;
        int dayOfYear = date.getDayOfYear();
        int year = date.getYear();
        LocalDateTime startDate = LocalDate.ofYearDay(year, dayOfYear).atStartOfDay();
        LocalDateTime finishDate = LocalDate.ofYearDay(year, dayOfYear).plusDays(PLUS_ONE_DAY).atStartOfDay();
        DayOfWeek weekDayEnum = DayOfWeek.valueOf(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US).toUpperCase());

        Optional<List<ReservationControl>> reservations = reservationControlGateway.findReservationsByRestaurantAndDate(restaurantId, startDate, finishDate);

        if (reservations.get().isEmpty()) {

            availableHours =  reservationControlBusiness.checkAvailableHoursByDayOfWeek(restaurant, weekDayEnum);

        }else{

            availableHours = reservationControlBusiness.checkAvailableHours(restaurant, reservations.get(), weekDayEnum, table);

        }

        return availableHours;

    }

    public ReservationDto schedule(String restaurantId, Integer table, LocalDate date, LocalTime hour, PersonDto dto) throws ValidationsException {

        Restaurant restaurant = restaurantGateway.findById(restaurantId);

        int dayOfYear = date.getDayOfYear();
        int year = date.getYear();
        int hours = hour.getHour();
        int minutes = hour.getMinute();
        LocalDateTime dateAndHour = LocalDate.ofYearDay(year, dayOfYear).atStartOfDay().plusHours(hours).plusMinutes(minutes);
        DayOfWeek weekDayEnum = DayOfWeek.valueOf(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US).toUpperCase());

        Optional<ReservationControl> reservationControl = reservationControlGateway.findReservationsByDateAndHour(restaurantId, dateAndHour);

        ReservationControl saveReservationControl;

        if (reservationControl.isEmpty()) {

            saveReservationControl = reservationControlBusiness.newReservationControl(restaurant, dateAndHour, hour, weekDayEnum, table);
            reservationControlGateway.insert(saveReservationControl);

        }else{

            reservationControlBusiness.checkReservationAvailability(reservationControl.get());

            saveReservationControl = reservationControlBusiness.updateReservationControlByNewReservation(reservationControl.get(), table);
            reservationControlGateway.save(saveReservationControl);

        }

        Reservation reservation = reservationControlBusiness.newReservation(restaurant, table, dateAndHour, weekDayEnum, dto);

        return reservationPresenter.convert(reservationGateway.insert(reservation));

    }

    public PagedResponse<ReservationDto> findByEmail(String email, Pagination page) throws ValidationsException{

        Pageable pageable = PageRequest.of(page.getPage(), page.getPageSize());

        Page<Reservation> reservation = reservationGateway.findAllByEmail(email, pageable);

        return reservationPresenter.convertDocuments(reservation);

    }

    public void reservationCancelation(String id) throws ValidationsException{

        Reservation reservation = reservationGateway.findById(id);

        String restaurantId = reservation.getRestaurant().getId();
        LocalDateTime reservationHour = reservation.getDateAndTime();
        Integer tableAmount = reservation.getReservationAmount();

        Optional<ReservationControl> reservationControl = reservationControlGateway.findReservationsByDateAndHour(restaurantId, reservationHour);

        reservationGateway.delete(reservation);

        ReservationControl updateReservationControl = reservationControlBusiness.updateReservationControlByReservationCancelation(reservationControl.get(), tableAmount);

        reservationControlGateway.save(updateReservationControl);

    }

}