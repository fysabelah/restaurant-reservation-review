package com.restaurant.reservationreview.controller.service;

import com.restaurant.reservationreview.model.repository.ReservationRepository;
import com.restaurant.reservationreview.util.converter.ReservationConverter;
import com.restaurant.reservationreview.util.dto.reservation.ReservationDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Resource
    private ReservationConverter reservationConverter;
    private ReservationRepository reservationRepository;

    public ReservationDto findByEmail(ReservationDto dto) throws ValidationsException{

        return reservationConverter.convert(reservationRepository.findByEmail(dto.getPersonDto().getEmail()));

    }

}
