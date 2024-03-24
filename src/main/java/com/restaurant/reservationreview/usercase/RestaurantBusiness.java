package com.restaurant.reservationreview.usercase;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.entities.Restaurant;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RestaurantBusiness {

    public void create(Optional<Restaurant> optional) throws ValidationsException {
        if (optional.isPresent()) {
            throw new ValidationsException("0201");
        }
    }

    public Restaurant update(Optional<Restaurant> optional, Restaurant restaurant, Restaurant toUpdate) throws ValidationsException {
        if (optional.isPresent() && restaurant.getId().compareTo(optional.get().getId()) != 0) {
            throw new ValidationsException("0206");
        }

        restaurant.setCapacity(toUpdate.getCapacity());
        restaurant.setName(toUpdate.getName());
        restaurant.setFoodType(toUpdate.getFoodType());
        restaurant.setAddress(toUpdate.getAddress());
        restaurant.setBusinnessHours(toUpdate.getBusinnessHours());
        restaurant.setHourToConsiderReservation(toUpdate.getHourToConsiderReservation());
        restaurant.setQuantityTablesAvailableReservation(toUpdate.getQuantityTablesAvailableReservation());

        return restaurant;
    }

    public void disable(List<Reservation> reservations) throws ValidationsException {
        if (reservations != null && !reservations.isEmpty()) {
            throw new ValidationsException("0207");
        }
    }
}
