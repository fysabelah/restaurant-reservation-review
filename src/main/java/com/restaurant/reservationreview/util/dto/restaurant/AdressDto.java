package com.restaurant.reservationreview.util.dto.restaurant;

import com.restaurant.reservationreview.util.dto.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdressDto extends Dto implements Serializable {

    private String street;

    private String adressNumber;

    private String city;

    private String state;

}
