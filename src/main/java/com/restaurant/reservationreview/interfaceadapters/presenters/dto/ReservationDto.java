package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.restaurant.reservationreview.util.enums.StatusReservation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"dayOfWeek"}, allowGetters = true)
public class ReservationDto extends Dto {

    private RestaurantDto restaurant;

    private PersonDto person;

    @Pattern(regexp = "[0-9]{4}//[0-9]{2}//[0-9]{2} [0-9]{2}:[0-9]{2}")
    @Schema(example = "2023/02/26 15:30")
    private String dateAndTime;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private DayOfWeek dayOfWeek;

    @Positive
    @Schema(example = "10")
    private Integer tables;

    private StatusReservation status;

    public ReservationDto(String id, RestaurantDto restaurant, PersonDto person, String dateAndTime, DayOfWeek dayOfWeek, Integer tables, StatusReservation status) {
        super(id);
        this.restaurant = restaurant;
        this.person = person;
        this.dateAndTime = dateAndTime;
        this.dayOfWeek = dayOfWeek;
        this.tables = tables;
        this.status = status;
    }
}
