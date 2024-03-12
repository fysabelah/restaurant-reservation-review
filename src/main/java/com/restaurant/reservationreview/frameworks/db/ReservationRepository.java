package com.restaurant.reservationreview.model.repository;

import com.restaurant.reservationreview.model.documents.Rating;
import com.restaurant.reservationreview.model.documents.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends MongoRepository<Rating, String> {

    @Query(value="{$and: [{'Restaurant.id':{$eq:?0}},{'dateAndTime':{$gte:?1}},{'dateAndTime':{$lte:?2}}]}",sort="{'dateAndTime':1}")
    public List<Reservation> findReservationDateAvailability(String id, LocalDateTime start, LocalDateTime finish);

}
