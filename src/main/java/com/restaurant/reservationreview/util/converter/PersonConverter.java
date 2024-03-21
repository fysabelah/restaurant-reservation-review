package com.restaurant.reservationreview.util.converter;

import com.restaurant.reservationreview.model.documents.Person;
import com.restaurant.reservationreview.model.documents.reservation.Reservation;
import com.restaurant.reservationreview.util.dto.PersonDto;
import com.restaurant.reservationreview.util.dto.reservation.ReservationDto;

public class PersonConverter implements Converter<Person, PersonDto>  {

    @Override
    public PersonDto convert(Person document) {
        return new PersonDto(
                document.getName(),
                document.getEmail(),
                document.getPhone());
    }

    @Override
    public Person convert(PersonDto dto) {
        return new Person(
        dto.getName(),
        dto.getEmail(),
        dto.getPhone());

    }

}
