package com.restaurant.reservationreview.framework.db;

import com.restaurant.reservationreview.entities.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    @Query(value="{'Reservation.Person.email':{$eq:?0}}")
    Reservation findByEmail(String email);

    Reservation insert(Reservation reservation);

}
