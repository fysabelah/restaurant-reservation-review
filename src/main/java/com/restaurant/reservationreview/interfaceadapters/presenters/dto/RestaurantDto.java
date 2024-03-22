package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class RestaurantDto extends Dto {

    public RestaurantDto(String id) {
        super(id);
    }
}
