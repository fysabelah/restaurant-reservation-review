package com.restaurant.reservationreview.model.documents.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationHours implements Serializable{

    private LocalTime hour;

    private Integer tableAmountAvailable;

}
