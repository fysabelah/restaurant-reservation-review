package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.BusinessHours;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.BusinnessHoursDto;
import com.restaurant.reservationreview.util.configs.time.TimeUtils;
import com.restaurant.reservationreview.util.exception.ValidationsException;
import org.springframework.stereotype.Component;

@Component
public class BusinessHoursPresenter implements Presenter<BusinessHours, BusinnessHoursDto> {

    @Override
    public BusinnessHoursDto convert(BusinessHours document) {
        return new BusinnessHoursDto(
                document.getDayOfWeek(),
                document.getOpeningTime().toString(),
                document.getClosingTime().toString()
        );
    }

    @Override
    public BusinessHours convert(BusinnessHoursDto dto) throws ValidationsException {
        return new BusinessHours(
                dto.getDayOfWeek(),
                TimeUtils.getTime(dto.getOpeningTime()),
                TimeUtils.getTime(dto.getClosingTime())
        );
    }
}
