package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.BusinnessHours;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.BusinnessHoursDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BusinessHoursPresenter implements Presenter<BusinnessHours, BusinnessHoursDto> {

    @Resource
    private ReservationHoursPresenter reservationHoursPresenter;

    @Override
    public BusinnessHoursDto convert(BusinnessHours document){

        return new BusinnessHoursDto(
                document.getDayOfWeek(),
                document.getStart(),
                document.getFinish(),
                document.getReservationHours()
                        .stream()
                        .map(reservationHours -> reservationHoursPresenter.convert(reservationHours))
                        .collect(Collectors.toList()),
                document.isAvailable()
        );
    }

    @Override
    public BusinnessHours convert(BusinnessHoursDto dto){

        return new BusinnessHours(
                dto.getDayOfWeek(),
                dto.getStart(),
                dto.getFinish(),
                dto.getReservationHoursDto()
                        .stream()
                        .map(reservationHoursDto -> reservationHoursPresenter.convert(reservationHoursDto))
                        .collect(Collectors.toList()),
                dto.isAvailable()
        );

    }

}
