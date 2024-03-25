package com.restaurant.reservationreview.frameworks.db;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.util.enums.StatusReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    List<Reservation> findAllByRestaurantIdAndStatusAndDateAfter(String restauranteId, StatusReservation wating, LocalDateTime date);

    Page<Reservation> findByRestaurantIdAndPersonName(String restaurant, String person, Pageable pageable);

    List<Reservation> findAllByRestaurantIdAndStatusAndDateBetween(String id, StatusReservation status, LocalDateTime timeRequired, LocalDateTime timeToConsider);
}
