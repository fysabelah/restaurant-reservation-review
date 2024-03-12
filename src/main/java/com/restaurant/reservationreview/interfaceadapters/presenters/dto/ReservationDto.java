package com.restaurant.reservationreview.util.dto;

import com.restaurant.reservationreview.model.documents.Person;
import com.restaurant.reservationreview.model.documents.Restaurant;
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
