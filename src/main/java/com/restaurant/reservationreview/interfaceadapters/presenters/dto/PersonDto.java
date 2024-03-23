package com.restaurant.reservationreview.util.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"id"})
public class PersonDto extends Dto implements Serializable {

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
