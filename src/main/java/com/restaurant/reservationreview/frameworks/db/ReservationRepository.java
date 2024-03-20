package com.restaurant.reservationreview.model.repository;

import com.restaurant.reservationreview.model.documents.reservation.Reservation;
import com.restaurant.reservationreview.model.documents.restaurant.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends MongoRepository<Rating, String> {

    @Query(value="{'Reservation.Person.email':{$eq:?0}}")
    Reservation findByEmail(String email);

    Reservation insert(Reservation reservation);

}
