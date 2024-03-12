package com.restaurant.reservationreview.model.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TotalReservationsByDay implements Serializable {

    private Date date;

    private DayOfWeek dayOfWeek;

    List<TotalReservationsByHour> totalReservationsByHourList;

}
