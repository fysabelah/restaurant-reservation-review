package com.restaurant.reservationreview.entities;

import com.restaurant.reservationreview.interfaceadapters.presenters.dto.PersonDto;
import com.restaurant.reservationreview.util.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Person implements Serializable {

    private String name;

    private String email;

    private String phone;

    public Person(PersonDto person) {
        this.email = person.getEmail();
        setName(person.getName());
        this.phone = person.getPhone();
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(MessageUtil.getMessage("0005"));
        }

        this.name = name;
    }
}
