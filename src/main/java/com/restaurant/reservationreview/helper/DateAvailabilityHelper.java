package com.restaurant.reservationreview.helper;

import com.restaurant.reservationreview.model.documents.*;
import com.restaurant.reservationreview.model.repository.ReservationRepository;
import jakarta.annotation.Resource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class DateAvailabilityHelper {

    private final static Integer PLUS_DAYS_THIRTY = 30;
    private final static Integer PLUS_DAYS_ONE = 1;

    @Resource
    private ReservationRepository reservationRepository;

    public List<LocalDate> checkDateAvailability(Restaurant restaurant, List<Reservation> reservations, Integer table){

        List<LocalDate> nextThirtyDays = nextThirtyDaysList();
        List<LocalDate> datesWithAvailability = new ArrayList<>();
        List<BusinnessHours> businnessHours = restaurant.getBusinnessHours();
//        List<ReservationHours> reservationHoursList = reservationHoursList(businnessHours);
        for (LocalDate date : nextThirtyDays) {

            DayOfWeek weekDayEnum = DayOfWeek.valueOf(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US).toUpperCase());
            if (checkRestaurantBusinessDays(businnessHours, weekDayEnum)) {
                if (checkTableAvailabilityByHour(businnessHours, weekDayEnum, reservations, date, table)) {
                    datesWithAvailability.add(date);
                }
            }
        }

        return datesWithAvailability;
    }

    private List<LocalDate> nextThirtyDaysList() {

        List<LocalDate> nextThirtyDays = new ArrayList<>();

        LocalDate tomorrow = LocalDate.now().plusDays(PLUS_DAYS_ONE);

        for(int i = 0; i <= PLUS_DAYS_THIRTY; i++){
            nextThirtyDays.add(tomorrow.plusDays(i));
        };

        return nextThirtyDays;

    }

    private boolean checkRestaurantBusinessDays(List<BusinnessHours> businnessHours, DayOfWeek dayOfWeek){

        for (BusinnessHours business : businnessHours) {
            if (business.getDayOfWeek() == dayOfWeek) {
                return true;
            }
        }
        return false;

    }
    private List<TotalReservationsByDay> createListWithTotalReservationsByDayAndHour(List<Reservation> reservations){

        Iterator<Reservation> iterator = reservations.iterator();

        Map<LocalDate, Map<LocalTime, Integer>> reservationstionsByDayAndHour = new HashMap<>();
        while (iterator.hasNext()) {
            Reservation reservation = iterator.next();
            LocalDate date = reservation.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalTime hour = reservation.getHour();
            int totalReservations = reservation.getReservationAmount();
            reservationstionsByDayAndHour.computeIfAbsent(date, k -> new HashMap<>())
                    .merge(hour, totalReservations, Integer::sum);
        }

        List<TotalReservationsByDay> reservationsByDates = new ArrayList<>();
        for (Map.Entry<LocalDate, Map<LocalTime, Integer>> entry : reservationstionsByDayAndHour.entrySet()) {
            LocalDate date = entry.getKey();
            Map<LocalTime, Integer> reservationsByHour = entry.getValue();
            List<TotalReservationsByHour> reservationsByHours = new ArrayList<>();
            for (Map.Entry<LocalTime, Integer> hourEntry : reservationsByHour.entrySet()) {
                LocalTime hour = hourEntry.getKey();
                int totalreservations = hourEntry.getValue();
                reservationsByHours.add(new reservationsByHour(hour, totalreservations));
            }
            reservationsByDates.add(new reservationsByDate(date, reservationsByHours));
        }

        return reservationsByDates;

    }
//      private List<ReservationHours> reservationHoursList(List<BusinnessHours> businessHours){
//        List<ReservationHours> reservationHoursList = new ArrayList<>();
//        List<ReservationHours> reservationHours;
//
//          for (BusinnessHours business : businessHours) {
//              reservationHours = business.getReservationHours();
//
//              for (ReservationHours reservation : reservationHours){
//                    reservationHoursList.add(reservation);
//                }
//            }
//
//          return reservationHoursList;
//      }

    private boolean checkTableAvailabilityByHour(List<BusinnessHours> businessHours, DayOfWeek dayOfWeek, List<Reservation> reservations, LocalDate date, Integer table){
        List<ReservationHours> reservationHoursList = new ArrayList<>();
        List<ReservationHours> reservationHours;

//        criar um objeto contendo o dia, dayOfWeek, horário e o total de mesas disponíveis por horário a partir de reservations e businesshours
//        comparar cada dia da lista de 30 dias com esse objeto, verificando

//        devolver todas as datas após verificar businessdays
//        ao receber uma data, verificar se existe horário com disponibilidade naquele dia


        businessHours.stream()
                .map(business -> business.getReservationHours())
                .collect(Collectors.toList()),
        for (BusinnessHours business : businessHours) {
          reservationHours = business.getReservationHours();

            for (ReservationHours reservation : reservationHours){
                reservationHoursList.add(reservation);
            }
        }
        for (int i = 0; i < PLUS_DAYS; i++) {
            Reservation reservation = reservations.get(i);
            if (checkTableAvailabilityByHour(restaurant, reservation, nextThirtyDays(i), table)) {
                datesWithAvailability.add(date);
            }
        }


            return false;
    }

}
