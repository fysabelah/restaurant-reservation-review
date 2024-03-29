package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.frameworks.db.ReservationRepository;
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
    private ReservationRepository reservationRepository;

    public Reservation insert(Reservation reservation) throws ValidationsException{

        return reservationRepository.insert(reservation);
    }
    public Page<Reservation> findAllByEmail(String email, Pageable pageable) throws ValidationsException{

        return reservationRepository.findAllByEmail(email, pageable);

    }

    public Reservation findById(String id) throws ValidationsException{

        return reservationRepository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "reserva", id));

    }

    public void save(Reservation reservation) throws ValidationsException{

        reservationRepository.save(reservation);

    }

    public Page<Reservation> findAllReservationsByRestaurantAndDateBetween(String restaurant, LocalDateTime start, LocalDateTime finish, Pageable page){

        return reservationRepository.findAllReservationsByRestaurantAndDateBetween(restaurant, start, finish, page);

    }

}