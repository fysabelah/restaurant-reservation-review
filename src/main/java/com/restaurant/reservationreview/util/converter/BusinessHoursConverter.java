package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.model.documents.restaurant.BusinnessHours;
import com.restaurant.reservationreview.util.dto.restaurant.BusinnessHoursDto;
import org.springframework.stereotype.Component;

@Component
public class BusinessHoursConverter implements Converter<BusinnessHours, BusinnessHoursDto> {

    @Override
    public BusinnessHoursDto convert(BusinnessHours document){

        return new BusinnessHoursDto(document.getStart(),
                document.getFinish(),
                document.getDayOfWeek()
        );
    }

    @Override
    public BusinnessHours convert(BusinnessHoursDto dto){

        return new BusinnessHours(dto.getStart(),
                dto.getFinish(),
                dto.getDayOfWeek()
        );

    }

}
