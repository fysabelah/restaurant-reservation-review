package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.ReservationControl;
import com.restaurant.reservationreview.frameworks.db.ReservationControlRepository;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationControlGateway {

    @Resource
    private ReservationControlRepository reservationControlRepository;

    public Optional<List<ReservationControl>> findReservationsByRestaurantAndDateNextDays(String restaurant, LocalDateTime start, LocalDateTime finish) {

        return reservationControlRepository.findByRestaurantIdAndDateAndTimeBetween(restaurant, start, finish);

    }

    public Optional<List<ReservationControl>> findReservationsByRestaurantAndDate(String restaurant, LocalDateTime start, LocalDateTime finish) {

        return reservationControlRepository.findReservationsByRestaurantAndDate(restaurant, start, finish);

    }

    public Optional<ReservationControl> findReservationsByDateAndHour(String restaurant, LocalDateTime dateAndHour) {

        return reservationControlRepository.findReservationsByDateAndHour(restaurant, dateAndHour);

    }

    public Page<ReservationControl> findAllReservationControlByRestaurantAndDateBetween(String restaurant, LocalDateTime start, LocalDateTime finish, Pageable page) {

        return reservationControlRepository.findByRestaurantIdAndDateAndTimeBetween(restaurant, start, finish, page);

    }

    public void save(ReservationControl updateReservationControl) {

        reservationControlRepository.save(updateReservationControl);

    }

    public void insert(ReservationControl newReservationControl) {

        reservationControlRepository.insert(newReservationControl);

    }

}