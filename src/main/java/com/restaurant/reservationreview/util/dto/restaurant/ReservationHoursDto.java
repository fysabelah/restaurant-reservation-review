package com.restaurant.reservationreview.util.dto.restaurant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restaurant.reservationreview.util.dto.Dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalTime;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"id"})
public class ReservationHoursDto extends Dto implements Serializable {

    private LocalTime hour;

    private Integer tableAmountAvailable;

}
