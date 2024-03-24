package com.restaurant.reservationreview.frameworks.db;

import com.restaurant.reservationreview.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    @Query(value="{'person.email':{$eq:?0}}")
    Page<Reservation> findAllByEmail(String email, Pageable page);

    Reservation insert(Reservation reservation);

    @Query(value="{ 'restaurant':{ $eq:?0 } }")
    Page<Reservation> findAllReservationsByRestaurantAndDateBetween(String restaurant, LocalDateTime start, LocalDateTime finish, Pageable page);

}