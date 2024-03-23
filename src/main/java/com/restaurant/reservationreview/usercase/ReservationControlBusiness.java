package com.restaurant.reservationreview.usercase;

import com.restaurant.reservationreview.entities.*;
import com.restaurant.reservationreview.interfaceadapters.presenters.PersonPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
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
public class ReservationControlBusiness {

    @Resource
    private PersonPresenter personPresenter;

    private final static Integer PLUS_RESERVATION_DAYS = 30;

    private final static Integer PLUS_ONE_DAY = 1;

    public List<LocalDate> checkDateAvailability(Restaurant restaurant, List<ReservationControl> reservations, Integer table) {

//        if (!reservations.isEmpty()) {

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

//        }else{
//
//            return nextDaysList();
//
//        }

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

//      ajustar para retornar apenas datas de businesshours

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

    public ReservationControl newReservationControl(Restaurant restaurant, LocalDateTime dateAndHour, LocalTime hour, DayOfWeek weekDayEnum, Integer table) {

        Integer capacity = getCapacityByHour(restaurant, weekDayEnum, hour);

        boolean available = false;

        if(capacity > table){
            available = true;
        }

//      atribui os valores para o controle de reservas para a data e hora recebidas
        ReservationControl newReservationControl = new ReservationControl();

        newReservationControl.setRestaurant(restaurant);
        newReservationControl.setDateAndTime(dateAndHour);
        newReservationControl.setDayOfWeek(weekDayEnum);
        newReservationControl.setTotalReservations(table);
        newReservationControl.setCapacity(capacity);
        newReservationControl.setAvailable(available);

        return newReservationControl;
    }

    public ReservationControl updateReservationControl(ReservationControl reservationControl, Integer table) {

        boolean available = false;

        Integer capacity = reservationControl.getCapacity();
        Integer totalReservations = reservationControl.getTotalReservations();
        Integer newTotalReservations = totalReservations + table;

        if(capacity > newTotalReservations){
            available = true;
        }

//      atribui os valores para o controle de reservas para a data e hora recebidas
        ReservationControl updateReservationControl = reservationControl;

        updateReservationControl.setTotalReservations(newTotalReservations);
        updateReservationControl.setAvailable(available);

        return updateReservationControl;
    }

    private Integer getCapacityByHour(Restaurant restaurant, DayOfWeek dayOfWeek, LocalTime hour) {

        List<BusinnessHours> businnessHours = restaurant.getBusinnessHours();
        Integer tableAmountAvaliable = 0;

        for (BusinnessHours business : businnessHours) {
            if (business.getDayOfWeek() == dayOfWeek) {
                List<ReservationHours> reservationHours = business.getReservationHours();
                for (ReservationHours reservationHours1 : reservationHours) {
                    if(reservationHours1.getHour() == hour);
                    tableAmountAvaliable = reservationHours1.getTableAmountAvailable();
                }
            }
        }

        return tableAmountAvaliable;

    }

    public Reservation newReservation(Restaurant restaurant, Integer table, LocalDateTime dateAndHour, DayOfWeek weekDayEnum, ReservationDto dto) {

        Reservation newReservation = new Reservation();

//      atribui os valores para o controle de reservas para a data e hora recebidas

        newReservation.setRestaurant(restaurant);
        newReservation.setPerson(personPresenter.convert(dto.getPersonDto()));
        newReservation.setDateAndTime(dateAndHour);
        newReservation.setDayOfWeek(weekDayEnum);
        newReservation.setReservationAmount(table);

        return newReservation;
    }



}
