package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import com.restaurant.reservationreview.util.enums.ReservationStatus;
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

    private ReservationStatus reservationStatus;

    public ReservationDto(String id, PersonDto personDto, LocalDateTime dateAndTime, DayOfWeek dayOfWeek, Integer reservationAmount, ReservationStatus reservationStatus) {
        super(id);
        this.personDto = personDto;
        this.dateAndTime = dateAndTime;
        this.dayOfWeek = dayOfWeek;
        this.reservationAmount = reservationAmount;
        this.reservationStatus = reservationStatus;
    }

    public ReservationDto(PersonDto personDto, LocalDateTime dateAndTime, DayOfWeek dayOfWeek, Integer reservationAmount, ReservationStatus reservationStatus) {
        this.personDto = personDto;
        this.dateAndTime = dateAndTime;
        this.dayOfWeek = dayOfWeek;
        this.reservationAmount = reservationAmount;
        this.reservationStatus = reservationStatus;
    }
}