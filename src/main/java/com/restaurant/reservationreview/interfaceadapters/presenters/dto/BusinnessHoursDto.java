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
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"id"})
public class BusinnessHoursDto extends Dto implements Serializable {

    @NotBlank(message = "O dia da semana é obrigatório")
    @Schema(example = "MONDAY")
    private DayOfWeek dayOfWeek;

    @Pattern(regexp = "[0-9]{2}:[0-9]{2}")
    @Schema(example = "17:00")
    @NotBlank(message = "O horário de funcionamento inicial é obrigatório (HH:MM)")
    private LocalTime start;

    @Pattern(regexp = "[0-9]{2}:[0-9]{2}")
    @Schema(example = "23:00")
    @NotBlank(message = "O horário de funcionamento Final é obrigatório (HH:MM)")
    private LocalTime finish;

    private List<ReservationHoursDto> reservationHoursDto;

    private boolean available;

}
