package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.frameworks.db.ReservationRepository;
import com.restaurant.reservationreview.util.enums.StatusReservation;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
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

    public void delete(Reservation rating) {
        repository.delete(rating);
    }

    public Reservation update(Reservation rating) {
        return repository.save(rating);
    }

    public List<Reservation> findAllByRestaurantIdAndDate(String restauranteId, LocalDateTime date) {
        return repository.findAllByRestaurantIdAndStatusAndDateAfter(restauranteId, StatusReservation.WATING, date);
    }
}
