package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.Address;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressPresenter implements Presenter<Address, AddressDto> {

    @Override
    public AddressDto convert(com.restaurant.reservationreview.entities.Address document) {
        return new AddressDto(
                document.getStreet(),
                document.getAdressNumber(),
                document.getCity(),
                document.getState()
        );
    }

    @Override
    public Address convert(AddressDto dto) {
        return new com.restaurant.reservationreview.entities.Address(
                dto.getStreet(),
                dto.getAdressNumber(),
                dto.getCity(),
                dto.getState()
        );
    }
}
