package com.restaurant.reservationreview.util.dto.reservation;

import com.restaurant.reservationreview.util.dto.Dto;
import com.restaurant.reservationreview.util.dto.PersonDto;
import com.restaurant.reservationreview.util.dto.restaurant.RestaurantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ReservationDto extends Dto implements Serializable {

    RestaurantDto restaurantDto;

    private PersonDto personDto;

    private LocalDateTime dateAndTime;

    private DayOfWeek dayOfWeek;

    private Integer reservationAmount;

}
