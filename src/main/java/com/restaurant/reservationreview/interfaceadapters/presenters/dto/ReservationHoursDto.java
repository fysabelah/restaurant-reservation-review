package com.restaurant.reservationreview.util.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"id"})
public class ReservationHoursDto extends Dto implements Serializable {

    private LocalTime hour;

    private Integer tableAmountAvailable;

}
