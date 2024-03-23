package com.restaurant.reservationreview.interfaceadapters.controllers;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.entities.ReservationControl;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.interfaceadapters.gateways.ReservationControlGateway;
import com.restaurant.reservationreview.interfaceadapters.gateways.ReservationGateway;
import com.restaurant.reservationreview.interfaceadapters.gateways.RestaurantGateway;
import com.restaurant.reservationreview.interfaceadapters.presenters.ReservationPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.usercase.ReservationControlBusiness;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
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

//      ajustar para consultar com o formato de datetime 2024-03-25T20:00:00.000+00:00
//      ou para salvar no formado 2024-03-24T00:00
        Optional<List<ReservationControl>> reservations = reservationControlGateway.findReservationsByRestaurantAndDateNextDays(restaurantId, startDate, finishDate);

        if (reservations.get().isEmpty()) {

            dates = reservationControlBusiness.nextDaysList();

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

        Optional<List<ReservationControl>> reservations = reservationControlGateway.findReservationsByDate(restaurantId, startDate, finishDate);

        if (reservations.get().isEmpty()) {

            availableHours =  reservationControlBusiness.checkAvailableHoursByDayOfWeek(restaurant, weekDayEnum);

        }else{

            availableHours = reservationControlBusiness.checkAvailableHours(restaurant, reservations.get(), weekDayEnum, table);

        }

        return availableHours;

    }

    public ReservationDto schedule(String restaurantId, Integer table, LocalDate date, LocalTime hour, ReservationDto dto) throws ValidationsException {

//        tentar usa o codium pra ajudar aqui
//        criar um atributo com a data e a hora recebidas, pra buscar em reservationControl
//          se existir, atualiza a disponibilidade de mesas para o horário
//          se não existir, criar registro para o dia e horário em reservationControl, pegando a quantidade de mesas disponíveis pelo restaurant.businessHours, de acordo com o dayOfWeek
//        no final, criar o registro da reserva

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

            saveReservationControl = reservationControlBusiness.updateReservationControl(reservationControl.get(), table);
            reservationControlGateway.save(saveReservationControl);

        }

//      atribui os valores da nova reserva para salvar
        Reservation reservation = reservationControlBusiness.newReservation(restaurant, table, dateAndHour, weekDayEnum, dto);

        return reservationPresenter.convert(reservationGateway.insert(reservation));

    }

    public ReservationDto findByEmail(ReservationDto dto) throws ValidationsException{

        String email = dto.getPersonDto().getEmail();

        Reservation reservation = reservationGateway.findByEmail(email);

        return reservationPresenter.convert(reservation);

    }
}
