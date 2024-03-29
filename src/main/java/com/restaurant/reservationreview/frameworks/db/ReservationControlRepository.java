package com.restaurant.reservationreview.frameworks.db;

import com.restaurant.reservationreview.entities.ReservationControl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationControlRepository extends MongoRepository<ReservationControl, String> {

    @Query(value="{ $and: [ {'restaurant':{ $eq:?0 } } , { 'dateAndTime': { $gte: { $date: ?1 }, $lte: { $date: ?2 } } } ] }",sort="{'dateAndTime':1}")
    Optional<List<ReservationControl>> findByRestaurantIdAndDateAndTimeBetween(String restaurant, LocalDateTime start, LocalDateTime finish);

    @Query(value="{$and: [{'restaurant':{$eq:?0}},{'dateAndTime':{$gte:?1}},{'dateAndTime':{$lte:?2}}]}",sort="{'dateAndTime':1}")
    Optional<List<ReservationControl>> findReservationsByRestaurantAndDate(String restaurant, LocalDateTime start, LocalDateTime finish);

    @Query(value="{$and: [{'restaurant':{$eq:?0}},{'dateAndTime':{$eq:?1}}]}")
    Optional<ReservationControl> findReservationsByDateAndHour(String restaurant, LocalDateTime dateAndHour);

    @Query(value="{ $and: [ {'restaurant':{ $eq:?0 } } , { 'dateAndTime': { $gte: { $date: ?1 }, $lte: { $date: ?2 } } } ] }",sort="{'dateAndTime':1}")
    Page<ReservationControl> findByRestaurantIdAndDateAndTimeBetween(String restaurant, LocalDateTime start, LocalDateTime finish, Pageable page);

}