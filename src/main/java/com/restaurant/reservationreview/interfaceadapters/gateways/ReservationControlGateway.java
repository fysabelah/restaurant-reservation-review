package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.ReservationControl;
import com.restaurant.reservationreview.frameworks.db.ReservationControlRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationControlGateway {

    @Resource
    private ReservationControlRepository reservationControlRepository;

    public Optional<List<ReservationControl>> findReservationsByRestaurantAndDateNextDays(String id, LocalDateTime start, LocalDateTime finish) {

        return reservationControlRepository.findByRestaurantIdAndDateAndTimeBetween(id, start, finish);

    }

    public Optional<List<ReservationControl>> findReservationsByRestaurantAndDate(String id, LocalDateTime start, LocalDateTime finish) {

        return reservationControlRepository.findReservationsByRestaurantAndDate(id, start, finish);

    }

    public Optional<ReservationControl> findReservationsByDateAndHour(String id, LocalDateTime dateAndHour) {

        return reservationControlRepository.findReservationsByDateAndHour(id, dateAndHour);

    }

    public void save(ReservationControl updateReservationControl) {

        reservationControlRepository.save(updateReservationControl);

    }

    public void insert(ReservationControl newReservationControl) {

        reservationControlRepository.insert(newReservationControl);

    }

}