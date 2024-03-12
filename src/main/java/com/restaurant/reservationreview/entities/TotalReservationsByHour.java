package com.restaurant.reservationreview.model.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class TotalReservationsByHour implements Serializable {

    private LocalTime hour;

    private Integer totalReservation;
}
