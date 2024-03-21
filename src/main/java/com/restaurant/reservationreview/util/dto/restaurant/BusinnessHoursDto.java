package com.restaurant.reservationreview.util.dto.restaurant;

import com.restaurant.reservationreview.util.dto.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BusinnessHoursDto extends Dto implements Serializable {

    private DayOfWeek dayOfWeek;

    private LocalTime start;

    private LocalTime finish;

    private List<ReservationHoursDto> reservationHoursDto;

    private boolean available;

}
