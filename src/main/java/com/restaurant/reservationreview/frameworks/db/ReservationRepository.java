package com.restaurant.reservationreview.frameworks.db;

import com.restaurant.reservationreview.entities.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationRepository extends MongoRepository<Reservation, String> {
}
