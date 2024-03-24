package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.frameworks.db.ReservationRepository;
import com.restaurant.reservationreview.frameworks.db.RestaurantRepository;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationGateway {

    @Resource
    private ReservationRepository reservationRepository;

    @Resource
    private RestaurantRepository restaurantRepository;

    public Reservation insert(Reservation reservation) throws ValidationsException{

        return reservationRepository.insert(reservation);
    }
    public Page<Reservation> findAllByEmail(String email, Pageable pageable) throws ValidationsException{

        return reservationRepository.findAllByEmail(email, pageable);

    }
    public List<Reservation> findAll() throws ValidationsException{

        return reservationRepository.findAll();

    }
    public Reservation findById(String id) throws ValidationsException{

        return reservationRepository.findById(id)
                .orElseThrow(() -> new ValidationsException("0001", "reserva", id));

    }

    public void delete(Reservation reservation) throws ValidationsException{

        reservationRepository.delete(reservation);

    }

    public Page<Reservation> findAll(String restaurant, Pageable page) throws ValidationsException{

        return reservationRepository.findAllByRestaurantId(restaurant, page);

    }

}
