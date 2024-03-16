package com.restaurant.reservationreview.helper;

import com.restaurant.reservationreview.model.documents.reservation.ReservationControl;
import com.restaurant.reservationreview.model.documents.restaurant.BusinnessHours;
import com.restaurant.reservationreview.model.documents.restaurant.ReservationHours;
import com.restaurant.reservationreview.model.documents.restaurant.Restaurant;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DateAndHourAvailabilityHelper {

    private final static Integer PLUS_RESERVATION_DAYS = 30;

    private final static Integer PLUS_ONE_DAY = 1;

    public List<LocalDate> checkDateAvailability(Restaurant restaurant, List<ReservationControl> reservations, Integer table) {

        List<LocalDate> nextThirtyDays = nextDaysList();
        List<LocalDate> datesWithAvailability = new ArrayList<>();
        List<BusinnessHours> businnessHours = restaurant.getBusinnessHours();

        for (LocalDate date : nextThirtyDays) {

            DayOfWeek weekDayEnum = DayOfWeek.valueOf(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US).toUpperCase());
            if (checkRestaurantBusinessDays(businnessHours, weekDayEnum)) {
                if (checkAvailableDates(reservations, date, table)) {
                    datesWithAvailability.add(date);
                }
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

    public List<LocalDate> nextDaysList() {

        List<LocalDate> nextThirtyDays = new ArrayList<>();

        LocalDate tomorrow = LocalDate.now().plusDays(PLUS_ONE_DAY);

        for (int i = 0; i <= PLUS_RESERVATION_DAYS; i++) {
            nextThirtyDays.add(tomorrow.plusDays(i));
        }

        return nextThirtyDays;

    }

    private boolean checkRestaurantBusinessDays(List<BusinnessHours> businnessHours, DayOfWeek dayOfWeek) {

        for (BusinnessHours business : businnessHours) {
            if (business.getDayOfWeek() == dayOfWeek) {
                return true;
            }
        }
        return false;

    }

    public List<LocalTime> checkAvailableHoursByDayOfWeek(Restaurant restaurant, DayOfWeek dayOfWeek) {

        List<BusinnessHours> businnessHours = restaurant.getBusinnessHours();
        List<LocalTime> availableHours = new ArrayList<>();

        for (BusinnessHours business : businnessHours) {
            if (business.getDayOfWeek() == dayOfWeek) {
                List<ReservationHours> reservationHours = business.getReservationHours();
                for (ReservationHours reservationHours1 : reservationHours) {
                    availableHours.add(reservationHours1.getHour());
                }
            }
        }

        return availableHours;

    }

    private boolean checkAvailableDates(List<ReservationControl> reservations, LocalDate date, Integer table) {

        boolean dateHasReservation = false;

        for (ReservationControl reservation : reservations) {
            if (date.getDayOfYear() == reservation.getDateAndTime().getDayOfYear()) {
                dateHasReservation = true;
                int diponibility = reservation.getCapacity() - reservation.getTotalReservations();
                if (diponibility > table) {
                    return true;
                }
            }
        }

        return !dateHasReservation;
//        if (!dateHasReservation) {
//            return true;
//        }
//
//        return false;

    }

    private boolean checkHourAvailability(List<ReservationControl> reservations, LocalTime hour, Integer table) {

        boolean hourHasReservation = false;
        String hourToCheck = String.format("%02d:%02d", hour.getHour(), hour.getMinute());

        for (ReservationControl reservation : reservations) {
            String bookedHour = String.format("%02d:%02d", reservation.getDateAndTime().getHour(),  reservation.getDateAndTime().getMinute());

            if (hourToCheck.equals(bookedHour)) {
                hourHasReservation = true;
                int diponibility = reservation.getCapacity() - reservation.getTotalReservations();
                if (diponibility > table) {
                    return true;
                }
            }
        }

        return !hourHasReservation;
//        if (!hourHasReservation) {
//            return true;
//        }
//
//        return false;

    }

}
