package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.entities.Reservation;
import com.restaurant.reservationreview.framework.db.ReservationRepository;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ReservationGateway {

    @Resource
    private ReservationRepository reservationRepository;

    public Reservation insert(Reservation reservation){

        return reservationRepository.insert(reservation);
    }
    public Reservation findByEmail(String email) throws ValidationsException{

        return reservationRepository.findByEmail(email);

    }

}
