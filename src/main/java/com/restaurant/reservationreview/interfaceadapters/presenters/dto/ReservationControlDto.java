package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"id"})
public class ReservationControlDto extends Dto implements Serializable {

    private String id;

    private String restaurantId;

    private String restaurantName;

    private LocalDateTime dateAndTime;

    private DayOfWeek dayOfWeek;

    private Integer totalReservations;

    private Integer capacity;

    private boolean available;

}