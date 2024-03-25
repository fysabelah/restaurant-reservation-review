package com.restaurant.reservationreview.usercase;

import com.restaurant.reservationreview.entities.BusinessHours;
import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.enums.StatusReservation;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReservationBusinness {

    public void updateStatus(StatusReservation status) throws ValidationsException {
        if (StatusReservation.CANCELED.equals(status)) {
            throw new ValidationsException("0304");
        }
    }

    public void validateOpeningHoursAndRestaurante(Reservation reservation, Restaurant restaurant) throws ValidationsException {
        // Valida restaurante desativado
        if (!restaurant.isActive()) {
            throw new ValidationsException("0307");
        }

        // Valida reserva para o passado
        if (reservation.getDate().isBefore(TimeUtils.now())) {
            throw new ValidationsException("0311");
        }

        DayOfWeek dayOfWeek = reservation.getDate().getDayOfWeek();
        reservation.setDayOfWeek(dayOfWeek);

        // Restaurante abre no dia solicitado
        BusinessHours businessHours = checkIfRestaurantIsOpenOnRequestedDay(dayOfWeek, restaurant);

        // Restaurante aberto no horário solicitado
        checkIfRequestedTimeIsWithinOpeningHours(businessHours, reservation.getDate());
    }

    private BusinessHours checkIfRestaurantIsOpenOnRequestedDay(DayOfWeek dayOfWeek, Restaurant restaurant) throws ValidationsException {
        BusinessHours businessHours = restaurant.getBusinessHourOfDay(dayOfWeek);

        if (businessHours == null) {
            throw new ValidationsException("0306", TimeUtils.getDayOfWeekInPortugues(dayOfWeek));
        }

        return businessHours;
    }

    private void checkIfRequestedTimeIsWithinOpeningHours(BusinessHours businessHours, LocalDateTime dateTime) throws ValidationsException {
        LocalDateTime opening = TimeUtils.getDate(dateTime).atTime(businessHours.getOpeningTime());
        LocalDateTime closing = TimeUtils.getDate(dateTime).atTime(businessHours.getClosingTime());

        if (businessHours.getClosingTime().isBefore(businessHours.getOpeningTime())) {
            closing = closing.plusDays(1);
        }

        if (dateTime.isBefore(opening) || dateTime.isAfter(closing)) {
            throw new ValidationsException("0308");
        }
    }

    public void validateTables(List<Reservation> reservations, Integer quantityTablesRestaurant, Integer quantityTablesReservation) throws ValidationsException {
        // Valida se tem reservas para o período
        if (!reservations.isEmpty()) {
            int tables = reservations.stream()
                    .mapToInt(Reservation::getTables)
                    .sum();

            // Restaurante está com todas reservas feitas para o período
            if (tables >= quantityTablesRestaurant) {
                throw new ValidationsException("0309");
            }

            int tablesAvailable = quantityTablesRestaurant - tables;

            // Valida se a quantidade de mesas solicitadas é compatível com a quantidade disponível
            if (tables == 0 || tablesAvailable < quantityTablesReservation) {
                throw new ValidationsException("0310", Integer.toString(tablesAvailable));
            }
        }
    }

    public void create(Reservation reservation, Restaurant restaurant) {
        reservation.setStatus(StatusReservation.WATING);
        reservation.setRestaurant(restaurant);
    }
}
