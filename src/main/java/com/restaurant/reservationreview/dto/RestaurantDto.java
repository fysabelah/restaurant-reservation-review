package com.restaurant.reservationreview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RestaurantDto extends br.com.gramado.parkingapp.dto.Dto {

    @NotBlank(message = "O campo Nome é obrigatório")
    @Schema(example = "Restaurante Comida Gostosa é aqui")
    private String name;

    @NotBlank(message = "O campo Localização é obrigatório")
    @Schema(example = "São Paulo")
    private String location;

    @NotBlank(message = "O campo Tipo de Cozinha é obrigatório")
    @Schema(example = "Brasileira / Japonesa / Francesa ")
    private String cuisineType;

    private Boolean active;

    private Integer averageRating;

    @NotBlank(message = "O campo Quantidade de Mesas é obrigatório")
    @Schema(example = "50 / 40 / 30")
    private Integer manytables;
}
