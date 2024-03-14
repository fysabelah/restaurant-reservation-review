package com.restaurant.reservationreview.util.dto.reservation;

import com.restaurant.reservationreview.model.documents.Person;
import com.restaurant.reservationreview.model.documents.restaurant.Restaurant;
import com.restaurant.reservationreview.util.dto.Dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ReservationDto extends Dto implements Serializable {

    Restaurant restaurant;

    private Person person;

    private LocalDateTime dateAndTime;

    private DayOfWeek dayOfWeek;

    private Integer reservationAmount;

}
