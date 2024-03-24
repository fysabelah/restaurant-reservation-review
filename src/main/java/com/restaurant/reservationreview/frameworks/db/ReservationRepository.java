package com.restaurant.reservationreview.frameworks.db;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.util.enums.StatusReservation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
    List<Reservation> findAllByRestaurantIdAndStatusAndDateAfter(String restauranteId, StatusReservation wating, LocalDateTime date);
}
