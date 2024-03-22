package com.restaurant.reservationreview.entities;

import lombok.Data;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class RestaurantBusinessHours implements Serializable {

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime finishTime;

}
