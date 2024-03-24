package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ReservationDto extends Dto implements Serializable {

    private PersonDto personDto;

    private LocalDateTime dateAndTime;

    private DayOfWeek dayOfWeek;

    private Integer reservationAmount;

    public ReservationDto(String id, PersonDto personDto, LocalDateTime dateAndTime, DayOfWeek dayOfWeek, Integer reservationAmount) {
        super(id);
        this.personDto = personDto;
        this.dateAndTime = dateAndTime;
        this.dayOfWeek = dayOfWeek;
        this.reservationAmount = reservationAmount;
    }

    public ReservationDto(PersonDto personDto, LocalDateTime dateAndTime, DayOfWeek dayOfWeek, Integer reservationAmount) {
        this.personDto = personDto;
        this.dateAndTime = dateAndTime;
        this.dayOfWeek = dayOfWeek;
        this.reservationAmount = reservationAmount;
    }
}