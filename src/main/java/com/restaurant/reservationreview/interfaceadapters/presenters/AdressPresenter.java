package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.Adress;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.AdressDto;
import org.springframework.stereotype.Component;

@Component
public class AdressPresenter implements Presenter<Adress, AdressDto> {

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
