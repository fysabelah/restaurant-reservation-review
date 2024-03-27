package com.restaurant.reservationreview.usercase;

import com.restaurant.reservationreview.util.enums.ReservationStatus;
import com.restaurant.reservationreview.entities.*;
import com.restaurant.reservationreview.interfaceadapters.presenters.PersonPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.PersonDto;
import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.constants.Constants;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ReservationBusiness {

    @Resource
    private PersonPresenter personPresenter;
    public ReservationBusiness(PersonPresenter personPresenter) {
        this.personPresenter = personPresenter;
    }
    public ReservationBusiness() {
    }
    private Constants constants;

    public List<LocalDate> checkDateAvailability(Restaurant restaurant, List<ReservationControl> reservations, Integer table) {

        List<LocalDate> nextThirtyDays = nextDaysList(restaurant);
        List<LocalDate> datesWithAvailability = new ArrayList<>();

        for (LocalDate date : nextThirtyDays) {

            DayOfWeek weekDayEnum = DayOfWeek.valueOf(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US).toUpperCase());
            if (checkAvailableDates(reservations, restaurant, date, weekDayEnum, table)) {
                datesWithAvailability.add(date);
            }
        }

        return datesWithAvailability;

    }

    public List<LocalTime> checkAvailableHours(Restaurant restaurant, List<ReservationControl> reservations, DayOfWeek dayOfWeek, Integer table) {

        List<LocalTime> bookableHours = checkAvailableHoursByDayOfWeek(restaurant, dayOfWeek);
        List<LocalTime> availableHours = new ArrayList<>();

        for (LocalTime hour : bookableHours) {

            if (checkHourAvailability(reservations, hour, table)) {
                availableHours.add(hour);
            }
        }

        return availableHours;
    }

    public List<LocalDate> nextDaysList(Restaurant restaurant) {

        List<LocalDate> businessDates = new ArrayList<>();
        List<BusinessHours> businessHours = restaurant.getBusinessHours();

        for (int i = 1; i <= constants.PLUS_RESERVATION_DAYS; i++) {

            LocalDate date = LocalDate.now().plusDays(i);

            DayOfWeek weekDayEnum = DayOfWeek.valueOf(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US).toUpperCase());
            if (checkRestaurantBusinessDays(businessHours, weekDayEnum)) {

                businessDates.add(date);

            }
        }

        return businessDates;

    }

    private boolean checkRestaurantBusinessDays(List<BusinessHours> businessHours, DayOfWeek dayOfWeek) {

        for (BusinessHours business : businessHours) {
            if (business.getDayOfWeek() == dayOfWeek && business.isAvailable()) {
                return true;
            }
        }
        return false;

    }

    public List<LocalTime> checkAvailableHoursByDayOfWeek(Restaurant restaurant, DayOfWeek dayOfWeek) {

        List<BusinessHours> businessHours = restaurant.getBusinessHours();
        List<LocalTime> availableHours = new ArrayList<>();

        for (BusinessHours business : businessHours) {
            if (business.getDayOfWeek() == dayOfWeek) {
                List<ReservationHours> reservationHours = business.getReservationHours();
                for (ReservationHours reservationHours1 : reservationHours) {
                    availableHours.add(reservationHours1.getHour());
                }
            }
        }

        return availableHours;

    }

    private boolean checkAvailableDates(List<ReservationControl> reservations, Restaurant restaurant, LocalDate date, DayOfWeek dayOfWeek, Integer table) {

        boolean dateHasReservation = false;

        int dayOfYear = date.getDayOfYear();
        int year = date.getYear();
        List<LocalTime> bookableHours = checkAvailableHoursByDayOfWeek(restaurant, dayOfWeek);

        for (ReservationControl reservation : reservations) {
            for(LocalTime bookablehour : bookableHours){
                int hours = bookablehour.getHour();
                int minutes = bookablehour.getMinute();
                LocalDateTime bookableDateAndHour = LocalDate.ofYearDay(year, dayOfYear).atStartOfDay().plusHours(hours).plusMinutes(minutes);
                if(bookableDateAndHour.toString().equals(reservation.getDateAndTime().toString())){
                    if (date.getDayOfYear() == reservation.getDateAndTime().getDayOfYear()) {
                        dateHasReservation = true;
                        int diponibility = reservation.getCapacity() - reservation.getTotalReservations();
                        if (diponibility > table) {
                            return true;
                        }
                    }
                }else{
                    checkHourAvailability(reservations, bookablehour, table);
                }
            }
        }

        return !dateHasReservation;

    }

    private boolean checkHourAvailability(List<ReservationControl> reservations, LocalTime hour, Integer table) {

        boolean hourHasReservation = false;

        for (ReservationControl reservation : reservations) {
            String bookedHour = String.format("%02d:%02d", reservation.getDateAndTime().getHour(),  reservation.getDateAndTime().getMinute());

            if (hour.toString().equals(bookedHour)) {
                hourHasReservation = true;
                if(reservation.isAvailable()) {
                    int diponibility = reservation.getCapacity() - reservation.getTotalReservations();
                    if (diponibility > table) {
                        return true;
                    }
                }
            }
        }

        return !hourHasReservation;

    }

    public ReservationControl newReservationControl(Restaurant restaurant, LocalDateTime dateAndHour, LocalTime hour, DayOfWeek weekDayEnum, Integer table) {

        Integer capacity = getCapacityByHour(restaurant, weekDayEnum, hour);

        boolean available = capacity > table;

        ReservationControl newReservationControl = new ReservationControl();


        newReservationControl.setRestaurant(restaurant);
        newReservationControl.setDateAndTime(dateAndHour);
        newReservationControl.setDayOfWeek(weekDayEnum);
        newReservationControl.setTotalReservations(table);
        newReservationControl.setCapacity(capacity);
        newReservationControl.setAvailable(available);

        return newReservationControl;
    }

    public static void checkReservationAvailability(ReservationControl reservationControl) throws ValidationsException {

        if(!reservationControl.isAvailable()){
            throw new IllegalArgumentException(MessageUtil.getMessage("0301"));
        }

    }

    public ReservationControl updateReservationControlByNewReservation(ReservationControl reservationControl, Integer table) {

        boolean available = false;

        Integer capacity = reservationControl.getCapacity();
        Integer totalReservations = reservationControl.getTotalReservations();
        totalReservations += table;

        if(capacity > totalReservations){
            available = true;
        }

        reservationControl.setTotalReservations(totalReservations);
        reservationControl.setAvailable(available);

        return reservationControl;
    }

    public ReservationControl updateReservationControlByReservationCancelation(ReservationControl reservationControl, Integer table) {

        boolean available = false;

        Integer capacity = reservationControl.getCapacity();
        Integer totalReservations = reservationControl.getTotalReservations();
        totalReservations -= table;

        if(capacity > totalReservations){
            available = true;
        }

        reservationControl.setTotalReservations(totalReservations);
        reservationControl.setAvailable(available);

        return reservationControl;
    }

    private Integer getCapacityByHour(Restaurant restaurant, DayOfWeek dayOfWeek, LocalTime hour) {

        List<BusinessHours> businessHours = restaurant.getBusinessHours();
        Integer tableAmountAvaliable = 0;

        for (BusinessHours business : businessHours) {
            if (business.getDayOfWeek() == dayOfWeek) {
                List<ReservationHours> reservationHours = business.getReservationHours();
                for (ReservationHours reservationHours1 : reservationHours) {
                    if(reservationHours1.getHour() == hour)
                        tableAmountAvaliable = reservationHours1.getTableAmountAvailable();
                }
            }
        }

        return tableAmountAvaliable;

    }

    public Reservation newReservation(Restaurant restaurant, Integer table, LocalDateTime dateAndHour, DayOfWeek weekDayEnum, PersonDto dto) {

        Reservation newReservation = new Reservation();

        if(!dto.getName().isEmpty()){
            throw new IllegalArgumentException(MessageUtil.getMessage("0302"));
        }
        if(!dto.getPhone().isEmpty()){
            throw new IllegalArgumentException(MessageUtil.getMessage("0303"));
        }
        if(!dto.getEmail().isEmpty()){
            throw new IllegalArgumentException(MessageUtil.getMessage("0304"));
        }

        newReservation.setRestaurant(restaurant);
        newReservation.setPerson(personPresenter.convert(dto));
        newReservation.setDateAndTime(dateAndHour);
        newReservation.setDayOfWeek(weekDayEnum);
        newReservation.setReservationAmount(table);
        newReservation.setReservationStatus(ReservationStatus.SCHEDULED);

        return newReservation;
    }

    public Reservation updateReservationCanceled(Reservation reservation) {

        reservation.setReservationStatus(ReservationStatus.CANCELED);

        return reservation;
    }

}