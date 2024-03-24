package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "[0-9]{2}:[0-9]{2}")
    @Schema(example = "17:00")
    @NotBlank(message = "O horário de reserva é obrigatório (HH:MM)")
    private LocalTime hour;

    @NotBlank(message = "O campo quantidade de mesas disponíveis é obrigatório")
    @Schema(example = "1")
    private Integer tableAmountAvailable;

}