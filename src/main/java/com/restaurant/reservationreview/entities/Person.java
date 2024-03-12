package com.restaurant.reservationreview.model.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class Person implements Serializable {

    private String name;

    private String email;

    private String phone;

}
