package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PersonDto extends Dto {

    @NotEmpty
    @Schema(example = "George")
    private String name;

    @NotEmpty
    @Schema(example = "george@mail.com.br")
    private String email;

    @NotEmpty
    @Schema(example = "11980809090")
    private String phone;

}
