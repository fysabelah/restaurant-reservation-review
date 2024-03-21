package com.restaurant.reservationreview.model.documents.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Adress implements Serializable {

    private String street;

    private String adressNumber;

    private String city;

    private String state;

}
