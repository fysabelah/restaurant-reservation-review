package com.restaurant.reservationreview.interfaceadapters.presenters;

import com.restaurant.reservationreview.entities.Person;
import com.restaurant.reservationreview.interfaceadapters.presenters.dto.PersonDto;
import org.springframework.stereotype.Component;

@Component
public class PersonPresenter implements Presenter<Person, PersonDto> {

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