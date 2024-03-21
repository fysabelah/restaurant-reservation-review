package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.model.documents.restaurant.BusinnessHours;
import com.restaurant.reservationreview.util.dto.restaurant.BusinnessHoursDto;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BusinessHoursConverter implements Converter<BusinnessHours, BusinnessHoursDto> {

    @Resource
    private ReservationHoursConverter reservationHoursConverter;

    @Override
    public BusinnessHoursDto convert(BusinnessHours document){

        return new BusinnessHoursDto(
                document.getDayOfWeek(),
                document.getStart(),
                document.getFinish(),
                document.getReservationHours()
                        .stream()
                        .map(reservationHours -> reservationHoursConverter.convert(reservationHours))
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
                        .map(reservationHoursDto -> reservationHoursConverter.convert(reservationHoursDto))
                        .collect(Collectors.toList()),
                dto.isAvailable()
        );

    }

}
