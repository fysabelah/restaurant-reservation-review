package com.restaurant.reservationreview.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AddressDto extends Dto {

    @NotBlank(message = "O nome da rua/avenida é obrigatório")
    @Schema(example = "Av Juscelino Kubitschek")
    private String street;

    @NotBlank(message = "O número é obrigatório")
    @Schema(example = "2041")
    private String adressNumber;

    @NotBlank(message = "A cidade é obrigatória")
    @Schema(example = "São Paulo")
    private String city;

    @NotBlank(message = "O estado é obrigatório")
    @Schema(example = "SP")
    private String state;

}
