package com.restaurant.reservationreview.helper;

import com.restaurant.reservationreview.model.documents.reservation.Reservations;
import com.restaurant.reservationreview.model.documents.reservation.ReservationControl;
import com.restaurant.reservationreview.model.documents.restaurant.BusinnessHours;
import com.restaurant.reservationreview.model.documents.restaurant.Restaurant;
import com.restaurant.reservationreview.model.repository.ReservationRepository;
import jakarta.annotation.Resource;

import java.time.*;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class DateAvailabilityHelper {

    private final static Integer PLUS_RESERVATION_DAYS = 30;

    private final static Integer PLUS_ONE_DAY = 1;

    @Resource
    private ReservationRepository reservationRepository;

    public List<LocalDate> checkDateAvailability(Restaurant restaurant, List<ReservationControl> reservations, Integer table){

        List<LocalDate> nextThirtyDays = nextThirtyDaysList();
        List<LocalDate> datesWithAvailability = new ArrayList<>();
        List<BusinnessHours> businnessHours = restaurant.getBusinnessHours();
//        List<ReservationHours> reservationHoursList = reservationHoursList(businnessHours);
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

    private List<LocalDate> nextThirtyDaysList() {

        List<LocalDate> nextThirtyDays = new ArrayList<>();

        LocalDate tomorrow = LocalDate.now().plusDays(PLUS_ONE_DAY);

        for(int i = 0; i <= PLUS_RESERVATION_DAYS; i++){
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

    private boolean checkAvailableDates(List<ReservationControl> reservations, LocalDate date, Integer table){

//     pra cada iteração, preciso encontrar o dia na lista de reservas
//     se não encontrar, retorno true
//     se encontrar, verifico se a quantidade de mesas solicitada está disponível
//          se tiver mesas disponíveis, retorno true

        for (ReservationControl reservation : reservations) {
            if(date.getDayOfYear() == reservation.getDateAndTime().getDayOfYear()){
                Integer diponibility = reservation.getCapacity() - reservation.getTotalReservations();
                if(diponibility > table){
                    return true;
                }
            }
        }

        return false;
    }

//    private boolean checkAvailableDates(List<BusinnessHours> businessHours, DayOfWeek dayOfWeek, List<ReservationControl> reservations, LocalDate date, Integer table){
////        List<ReservationHours> reservationHoursList = new ArrayList<>();
////        List<ReservationHours> reservationHours;
//
////        criar um objeto contendo o dia, dayOfWeek, horário e o total de mesas disponíveis por horário a partir de reservations e businesshours
////        comparar cada dia da lista de 30 dias com esse objeto, verificando
//
////        devolver todas as datas após verificar businessdays
////        ao receber uma data, verificar se existe horário com disponibilidade naquele dia
//
//
//        businessHours.stream()
//                .map(business -> business.getReservationHours())
//                .collect(Collectors.toList());
//        for (BusinnessHours business : businessHours) {
//            reservationHours = business.getReservationHours();
//
//            for (ReservationHours reservation : reservationHours){
//                reservationHoursList.add(reservation);
//            }
//        }
//        for (int i = 0; i < PLUS_DAYS; i++) {
//            Reservation reservation = reservations.get(i);
//            if (checkTableAvailabilityByHour(restaurant, reservation, nextThirtyDays(i), table)) {
//                datesWithAvailability.add(date);
//            }
//        }
//
//
//        return false;
//    }

//    private List<ReservationControl> createListWithTotalReservationsByDayAndHour(List<Reservations> reservations){
//
//        Map<LocalDate, Map<Integer, Integer>> hourlyReservations = new HashMap<>();
//
//        for (Reservations reservation : reservations) {
//            LocalDateTime dateTime = reservation.getDateAndTime();
//            LocalDate date = dateTime.toLocalDate();
//            int hour = dateTime.getHour();
//            int amount = reservation.getReservationAmount();
//
//            if (!hourlyReservations.containsKey(date)) {
//                hourlyReservations.put(date, new HashMap<>());
//            }
//
//            Map<Integer, Integer> hourlyMap = hourlyReservations.get(date);
//            hourlyMap.put(hour, hourlyMap.getOrDefault(hour, 0) + amount);
//        }
//
//        List<Reservations> reservations = /* Código para obter as reservas do MongoDB */ null;
//
//        ReservationProcessor processor = new ReservationProcessor();
//        Map<LocalDate, Map<Integer, Integer>> hourlyReservations = processor.calculateHourlyReservations(reservations);
//
//        // Agora você pode iterar sobre o mapa e imprimir os resultados, por exemplo
//        for (Map.Entry<LocalDate, Map<Integer, Integer>> entry : hourlyReservations.entrySet()) {
//            LocalDate date = entry.getKey();
//            Map<Integer, Integer> hourlyMap = entry.getValue();
//            System.out.println("Reservas para o dia " + date + ":");
//            for (Map.Entry<Integer, Integer> hourlyEntry : hourlyMap.entrySet()) {
//                int hour = hourlyEntry.getKey();
//                int amount = hourlyEntry.getValue();
//                System.out.println("- Hora " + hour + ": " + amount + " reservas");
//            }
//        }
//
//        return reservationsByDates;
//
//    }

//    private List<TotalReservationsByDay> createListWithTotalReservationsByDayAndHour(List<Reservation> reservations){
//
//        Iterator<Reservation> iterator = reservations.iterator();
//
//        Map<LocalDate, Map<LocalTime, Integer>> reservationstionsByDayAndHour = new HashMap<>();
//        while (iterator.hasNext()) {
//            Reservation reservation = iterator.next();
//            LocalDate date = reservation.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            LocalTime hour = reservation.getHour();
//            int totalReservations = reservation.getReservationAmount();
//            reservationstionsByDayAndHour.computeIfAbsent(date, k -> new HashMap<>())
//                    .merge(hour, totalReservations, Integer::sum);
//        }
//
//        List<TotalReservationsByDay> reservationsByDates = new ArrayList<>();
//        for (Map.Entry<LocalDate, Map<LocalTime, Integer>> entry : reservationstionsByDayAndHour.entrySet()) {
//            LocalDate date = entry.getKey();
//            Map<LocalTime, Integer> reservationsByHour = entry.getValue();
//            List<TotalReservationsByHour> reservationsByHours = new ArrayList<>();
//            for (Map.Entry<LocalTime, Integer> hourEntry : reservationsByHour.entrySet()) {
//                LocalTime hour = hourEntry.getKey();
//                int totalreservations = hourEntry.getValue();
//                reservationsByHours.add(new reservationsByHour(hour, totalreservations));
//            }
//            reservationsByDates.add(new reservationsByDate(date, reservationsByHours));
//        }
//
//        return reservationsByDates;
//
//    }

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

}
