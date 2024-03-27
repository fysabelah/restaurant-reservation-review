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
@Getter
@Setter
public class ReservationControlDto extends Dto implements Serializable {
    public ReservationControlDto(String id, String restaurantId, String restaurantName, LocalDateTime dateAndTime, DayOfWeek dayOfWeek, Integer totalReservations, Integer capacity, boolean available) {
        super(id);
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.dateAndTime = dateAndTime;
        this.dayOfWeek = dayOfWeek;
        this.totalReservations = totalReservations;
        this.capacity = capacity;
        this.available = available;
    }

    public ReservationControlDto(String restaurantId, String restaurantName, LocalDateTime dateAndTime, DayOfWeek dayOfWeek, Integer totalReservations, Integer capacity, boolean available) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.dateAndTime = dateAndTime;
        this.dayOfWeek = dayOfWeek;
        this.totalReservations = totalReservations;
        this.capacity = capacity;
        this.available = available;
    }

    private String restaurantId;

    private String restaurantName;

    private LocalDateTime dateAndTime;

    private DayOfWeek dayOfWeek;

    private Integer totalReservations;

    private Integer capacity;

    private boolean available;

}