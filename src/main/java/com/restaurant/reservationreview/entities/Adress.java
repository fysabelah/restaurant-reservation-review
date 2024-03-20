package com.restaurant.reservationreview.model.documents;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Adress {

    private String street;

    private String adressNumber;

    private String city;

    private String state;

}
