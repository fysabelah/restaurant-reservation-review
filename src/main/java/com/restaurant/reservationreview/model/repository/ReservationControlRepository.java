package com.restaurant.reservationreview.model.repository;

import com.restaurant.reservationreview.model.documents.reservation.ReservationControl;
import com.restaurant.reservationreview.model.documents.restaurant.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationControlRepository extends MongoRepository<Rating, String> {

//    @Query("{ 'restaurant.$id': ?0, 'dateAndTime': { $gte: ?1, $lte: ?2 }, 'available': true }")
    @Query(value="{$and: [{'Restaurant.id':{$eq:?0}},{'dateAndTime':{$gte:?1}},{'dateAndTime':{$lte:?2}},{'available':{$eq:true}}]}",sort="{'dateAndTime':1}")
    Optional<List<ReservationControl>> findAvailableReservationDates(String id, LocalDateTime start, LocalDateTime finish);

    @Query(value="{$and: [{'Restaurant.id':{$eq:?0}},{'dateAndTime':{$gte:?1}},{'dateAndTime':{$lte:?2}},{'available':{$eq:true}}]}",sort="{'dateAndTime':1}")
    Optional<List<ReservationControl>> findReservationsByDate(String id, LocalDateTime start, LocalDateTime finish);

    @Query(value="{$and: [{'Restaurant.id':{$eq:?0}},{'dateAndTime':{$eq:?1}}")
    Optional<ReservationControl> findReservationsByDateAndHour(String id, LocalDateTime dateAndHour);

    void insert(ReservationControl reservationControl);

    void update(ReservationControl reservationControl);

}
