package com.restaurant.reservationreview.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class BusinnessHours implements Serializable {

    private DayOfWeek dayOfWeek;

    private LocalTime start;

    private LocalTime finish;

    private List<ReservationHours> reservationHours;

    private boolean available;

}