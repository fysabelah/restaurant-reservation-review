package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.frameworks.db.ReservationRepository;
import com.restaurant.reservationreview.util.MessageUtil;
import com.restaurant.reservationreview.util.enums.StatusReservation;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationGateway {

    @Resource
    private ReservationRepository repository;

    public Reservation insert(Reservation rating) {
        return repository.insert(rating);
    }

    public Reservation findById(String id) throws ValidationsException {
        return repository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "reserva", id));
    }

    public Reservation update(Reservation rating) {
        return repository.save(rating);
    }

    public List<Reservation> findAllByRestaurantIdAndDate(String restauranteId, LocalDateTime date) {
        return repository.findAllByRestaurantIdAndStatusAndDateAfter(restauranteId, StatusReservation.WATING, date);
    }

    public Page<Reservation> findAll(String restaurant, String person, Pageable pageable) {
        if (person == null || restaurant == null) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0004"));
        }

        return repository.findByRestaurantIdAndPersonName(restaurant, person, pageable);
    }

    public List<Reservation> findAllReservationOfPeriod(String restaurant, LocalDateTime start, LocalDateTime end) {
        return repository.findAllByRestaurantIdAndStatusAndDateBetween(restaurant, StatusReservation.WATING, start, end);
    }
}
