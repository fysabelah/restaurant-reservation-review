package com.restaurant.reservationreview.util.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"id"})

public class RestaurantBusinessHoursDto extends Dto {

    @NotBlank(message = "O dia da semana é obrigatório")
    @Schema(example = "MONDAY")
    private DayOfWeek dayOfWeek;

    @Pattern(regexp = "[0-9]{2}:[0-9]{2}")
    @Schema(example = "17:00")
    @NotBlank(message = "O horário de funcionamento inicial é obrigatório (HH:MM)")
    private String startTime;

    @Pattern(regexp = "[0-9]{2}:[0-9]{2}")
    @Schema(example = "23:00")
    @NotBlank(message = "O horário de funcionamento Final é obrigatório (HH:MM)")
    private String finishTime;

}
