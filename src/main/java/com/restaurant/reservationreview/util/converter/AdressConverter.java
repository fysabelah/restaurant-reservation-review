package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.model.documents.restaurant.Adress;
import com.restaurant.reservationreview.model.documents.restaurant.BusinnessHours;
import com.restaurant.reservationreview.util.dto.restaurant.AdressDto;
import com.restaurant.reservationreview.util.dto.restaurant.BusinnessHoursDto;
import org.springframework.stereotype.Component;

@Component
public class AdressConverter implements Converter<Adress, AdressDto> {

    @Override
    public AdressDto convert(Adress document){

        return new AdressDto(
                document.getStreet(),
                document.getAdressNumber(),
                document.getCity(),
                document.getState()
        );
    }

    @Override
    public Adress convert(AdressDto dto){

        return new Adress(
                dto.getStreet(),
                dto.getAdressNumber(),
                dto.getCity(),
                dto.getState()
        );

    }
}
