package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.restaurant.reservationreview.util.enums.StatusReservation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = {"dayOfWeek", "status", "address", "restaurant"}, allowGetters = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ReservationDto extends Dto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String restaurant;

    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    @NotBlank
    private String restaurantId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private AddressDto address;

    private PersonDto person;

    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}")
    @Schema(example = "2023-02-26T15:30")
    private String dateAndTime;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private DayOfWeek dayOfWeek;

    @Positive
    @Schema(example = "10")
    private Integer tables;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private StatusReservation status;
}
