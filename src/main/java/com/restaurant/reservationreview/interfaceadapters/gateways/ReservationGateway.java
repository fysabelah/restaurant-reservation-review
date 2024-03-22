package com.restaurant.reservationreview.interfaceadapters.gateways;

import com.restaurant.reservationreview.framework.db.ReservationRepository;
import com.restaurant.reservationreview.interfaceadapters.presenters.ReservationPresenter;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.ReservationDto;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class ReservationGateway {

    @Resource
    private ReservationPresenter reservationPresenter;
    @Resource
    private ReservationRepository reservationRepository;

    public ReservationDto findByEmail(ReservationDto dto) throws ValidationsException{

        return reservationPresenter.convert(reservationRepository.findByEmail(dto.getPersonDto().getEmail()));

    }

}
