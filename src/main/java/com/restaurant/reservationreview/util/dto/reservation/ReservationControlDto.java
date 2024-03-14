package com.restaurant.reservationreview.util.dto.reservation;

import com.restaurant.reservationreview.model.documents.restaurant.Restaurant;
import com.restaurant.reservationreview.util.dto.Dto;
import com.restaurant.reservationreview.util.dto.restaurant.RestaurantDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ReservationControlDto extends Dto implements Serializable {

    private RestaurantDto restaurantDto;

    private LocalDateTime dateAndTime;

    private DayOfWeek dayOfWeek;

    private Integer totalReservations;

    private Integer capacity;

    private boolean available;

}
