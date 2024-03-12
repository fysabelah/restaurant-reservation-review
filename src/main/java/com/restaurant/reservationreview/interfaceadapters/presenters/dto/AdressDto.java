package com.restaurant.reservationreview.util.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AdressDto extends Dto {

    private String street;

    private String adressNumber;

    private String city;

    private String state;

}
